package cn.ilpanda.arch.debug.ui

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.ilpanda.arch.extension.roundTo
import cn.ilpanda.arch.R
import cn.ilpanda.arch.debug.baes.BaseUIActivity
import com.ilpanda.arch.common.utils.*
import okio.buffer
import okio.source
import java.io.File
import java.util.*

class PhoneInfoActivity : BaseUIActivity() {

    lateinit var rlyScreenRoot: RelativeLayout

    lateinit var mContent: FrameLayout

    lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        QMUIStatusBarHelper.translucent(this)
        setContentView(R.layout.tm_activity_screen_size)
        supportTransparentBar()
        showLeftBack()
        setCenterTitle("手机信息")

        rlyScreenRoot = findViewById(R.id.rly_screen_root)
        mContent = findViewById(android.R.id.content)
        mRecyclerView = findViewById(R.id.recycler_view)

        rlyScreenRoot.post { // 获取 View 的 x 坐标和 y 坐标,这个坐标是距离手机左上角的坐标系,而不是距离父 View 的 x、y 偏移.
            initView()
        }
    }

    @SuppressLint("DefaultLocale")
    private fun initView() {
        val displayMetrics = mContext.resources.displayMetrics
        val list = ArrayList<PhoneDataInfo>()

        // 屏幕信息
        list.add(PhoneDataInfo("屏幕信息", isHeader = true))
        val phoneInfoActivity = this
        list.add(PhoneDataInfo("屏幕宽度：", ScreenUtil.getScreenWidth(phoneInfoActivity).toString() + "px"))
        list.add(PhoneDataInfo("屏幕高度：", ScreenUtil.getScreenHeight(phoneInfoActivity).toString() + "px"))
        list.add(PhoneDataInfo("屏幕实际宽度：", ScreenUtil.getRealScreenWidth(phoneInfoActivity).toString() + "px"))
        list.add(PhoneDataInfo("屏幕实际高度：", ScreenUtil.getRealScreenHeight(phoneInfoActivity).toString() + "px"))
        list.add(PhoneDataInfo("是否存在导航栏：", ScreenUtil.existsNavigationBar(phoneInfoActivity).toString() + ""))
        list.add(PhoneDataInfo("导航栏高度：", ScreenUtil.getNavigationBarHeight(phoneInfoActivity).toString() + "px"))
        list.add(PhoneDataInfo("状态栏高度：", ScreenUtil.getStatusBarHeight(phoneInfoActivity).toString() + "px"))
        list.add(PhoneDataInfo("华为手机：", QMUIDeviceHelper.isHuawei().toString()))
        list.add(PhoneDataInfo("rlyScreenRoot Height:", rlyScreenRoot.height.toString() + "px"))
        list.add(PhoneDataInfo("Android Content Height:", mContent.height.toString() + "px"))
        list.add(PhoneDataInfo("密度:", displayMetrics.density.toString()))
        list.add(PhoneDataInfo("屏幕密度:", displayMetrics.densityDpi.toString() + "Dpi"))

        // 内存信息
        list.add(PhoneDataInfo("应用内存信息", isHeader = true))
        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        list.add(PhoneDataInfo("最大可用堆内存:", activityManager.memoryClass.toString() + "M"))
        list.add(PhoneDataInfo("largeHeap 可用时堆内存:", activityManager.largeMemoryClass.toString() + "M"))
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)

        // 获取 App 运行时的内存
        val total = Runtime.getRuntime().totalMemory() / 1.0f / (1024 * 1024)
        val free = Runtime.getRuntime().freeMemory() / 1.0f / (1024 * 1024)
        val max = Runtime.getRuntime().maxMemory() / 1.0f / (1024 * 1024)
        list.add(PhoneDataInfo("已申请最大内存 total:", total.roundTo(2).toString() + "M")) // 已申请的：包含已使用的的和未使用的。
        list.add(PhoneDataInfo("申请后剩余的内存 free:", free.roundTo(2).toString() + "M")) // 申请后但未使用的内存。
        list.add(PhoneDataInfo("最大可使用内存 max:", max.toString() + "M"))

        // 获取手机内存
        list.add(PhoneDataInfo("手机内存信息", isHeader = true))
        list.add(PhoneDataInfo("手机总内存:", (memoryInfo.totalMem / 1.0f / (1024 * 1024 * 1024)).roundTo(2).toString() + "G"))
        list.add(PhoneDataInfo("手机可用内存:", (memoryInfo.availMem / 1.0f / (1024 * 1024 * 1024)).roundTo(2).toString() + "G"))
        list.add(PhoneDataInfo("手机总磁盘空间:", Utils.getExternalTotalSize(phoneInfoActivity)))
        list.add(PhoneDataInfo("手机可用磁盘空间 :", Utils.getExternalAvailableSize(phoneInfoActivity)))

        // device
        list.add(PhoneDataInfo("设备信息", isHeader = true))
        list.add(PhoneDataInfo("设备厂商 :", DeviceUtil.getManufacturer()))
        list.add(PhoneDataInfo("设备型号 :", DeviceUtil.getModel()))
        list.add(PhoneDataInfo("系统版本码 :", DeviceUtil.getSDKVersionName()))
        list.add(PhoneDataInfo("系统版本号", DeviceUtil.getSDKVersionCode().toString() + ""))
        list.add(PhoneDataInfo("CPU 核数 :", Runtime.getRuntime().availableProcessors().toString()))
        list.add(PhoneDataInfo("ABIS :", Arrays.toString(DeviceUtil.getABIs())))
        list.add(PhoneDataInfo("Root :", DeviceUtil.isDeviceRooted().toString()))
        list.add(PhoneDataInfo("ADB enable :", DeviceUtil.isAdbEnabled(phoneInfoActivity).toString()))
        list.add(PhoneDataInfo("开发者模式 :", DeviceUtil.isDevelopmentSettingsEnabled(phoneInfoActivity).toString()))
        list.add(PhoneDataInfo("模拟器 :", DeviceUtil.isEmulator(phoneInfoActivity).toString()))
        list.add(PhoneDataInfo("平板 :", DeviceUtil.isTablet().toString()))

        // App Info
        var appInfo = AppInfoUtil.getAppInfo(phoneInfoActivity)

        list.add(PhoneDataInfo("App 信息", isHeader = true))
        list.add(PhoneDataInfo("App 包名 :", appInfo?.packageName))
        list.add(PhoneDataInfo("App 名称 :", appInfo?.name))
        list.add(PhoneDataInfo("App 版本名称 :", appInfo?.versionName))
        list.add(PhoneDataInfo("App 版本号 :", appInfo?.versionCode.toString()))

        list.add(PhoneDataInfo("是否为系统 App:", appInfo?.isSystem.toString()))
        list.add(PhoneDataInfo("是否为Debug App:", AppInfoUtil.isAppDebug(phoneInfoActivity).toString()))
        list.add(PhoneDataInfo("应用签名的的 MD5 值", AppInfoUtil.getAppSignaturesMD5(phoneInfoActivity, packageName)[0]))
        list.add(PhoneDataInfo("应用签名的的 SHA1 值", AppInfoUtil.getAppSignaturesSHA1(phoneInfoActivity, packageName)[0]))
        list.add(PhoneDataInfo("应用签名的的 SHA256 值", AppInfoUtil.getAppSignaturesSHA256(phoneInfoActivity, packageName)[0]))
        list.add(PhoneDataInfo("应用签名的的 SHA512 值", AppInfoUtil.getAppSignaturesSHA512(phoneInfoActivity, packageName)[0]))

        val adapter = PhoneInfoAdapter(R.layout.tm_item_phone_section_info, R.layout.tm_item_phone_info, list)
        mRecyclerView.layoutManager = LinearLayoutManager(mContext)
        mRecyclerView.adapter = adapter
    }


    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, PhoneInfoActivity::class.java)
            context.startActivity(starter)
        }
    }


    class CpuInfo(var key: String, var value: String)

    private fun cpuInfo(): MutableList<CpuInfo> {
        var cpuList = mutableListOf<CpuInfo>()
        File("/proc/cpuinfo").source().buffer().use {
            while (!it.exhausted()) {
                val line = it.readUtf8Line()
                line?.let {
                    val array = line.split(":")
                    if (array.size > 1) {
                        cpuList.add(CpuInfo(array[0], array[1]))
                    }
                }
            }
        }
        return cpuList
    }
}