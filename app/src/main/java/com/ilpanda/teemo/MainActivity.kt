package com.ilpanda.teemo

import android.os.Bundle
import cn.ilpanda.arch_debug.kotlin.baes.BaseUIActivity
import cn.ilpanda.arch_debug.kotlin.ui.appinfo.PhoneInfoActivity
import com.ilpanda.arch_common.kotlin.start

class MainActivity : BaseUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext.start<PhoneInfoActivity>()

    }
}