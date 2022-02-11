package cn.ilpanda.arch.debug.baes

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import cn.ilpanda.arch.R
import com.ilpanda.arch.common.utils.ScreenUtil
import com.ilpanda.arch.common.kotlin.setSafeOnClick

open class BaseUIActivity : BaseActivity() {

    @JvmField
    var TAG = this.javaClass.simpleName

    lateinit var mContext: Context;

    lateinit var mTvBack: TextView

    lateinit var mLLBaseRoot: LinearLayout

    lateinit var mTvCenter: TextView

    lateinit var mRlyTitleBar: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mContext = this
        Log.i(TAG, "onCreate: ")
    }

    override fun setContentView(view: View) {
        val rootView = LayoutInflater.from(this).inflate(R.layout.tm_activity_base, null, false)
        val frameLayout = rootView.findViewById<View>(R.id.fl_root) as FrameLayout
        frameLayout.addView(view)
        super.setContentView(rootView)
        initView()
    }

    override fun setContentView(layoutResID: Int) {
        val rootView = LayoutInflater.from(this).inflate(R.layout.tm_activity_base, null, false) as ViewGroup
        val addView = LayoutInflater.from(this).inflate(layoutResID, rootView, false)
        val frameLayout = rootView.findViewById<View>(R.id.fl_root) as FrameLayout
        frameLayout.addView(addView)
        super.setContentView(rootView)
        initView()
    }

    private fun initView() {
        mTvBack = findViewById(R.id.tv_back)
        mLLBaseRoot = findViewById(R.id.ll_base_root)
        mTvCenter = findViewById(R.id.tv_center)
        mRlyTitleBar = findViewById(R.id.rly_title_bar)
        mTvBack.setSafeOnClick {
            super.onBackPressed()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState: ")
    }

    override fun onRestart() {
        super.onRestart()
        Log.i(TAG, "onRestart: ")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "onResume: ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "onPause: ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "onStop: ")
    }

    override fun onDestroy() {
        Log.i(TAG, "onDestroy: ")
        super.onDestroy()
    }

    protected fun setCenterTitle(resId: Int) {
        mTvCenter.setText(resId)
    }

    protected fun setCenterTitle(title: String?) {
        mTvCenter.text = title
    }

    protected fun showLeftBack() {
        mTvBack.visibility = View.VISIBLE
    }

    protected fun supportTransparentBar() {
        val paddingTop = ScreenUtil.getStatusBarHeight(this)
        val layoutParams = mRlyTitleBar.layoutParams
        layoutParams.height += paddingTop
        mRlyTitleBar.setPadding(mRlyTitleBar.paddingLeft, paddingTop, mRlyTitleBar.paddingRight, mRlyTitleBar.paddingBottom)
    }

    fun hideTitleBar() {
        mRlyTitleBar.visibility = View.GONE
    }

}