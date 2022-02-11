package com.ilpanda.teemo

import android.os.Bundle
import cn.ilpanda.arch.debug.baes.BaseUIActivity
import cn.ilpanda.arch.debug.ui.PhoneInfoActivity
import com.ilpanda.arch.common.kotlin.start

class MainActivity : BaseUIActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mContext.start<PhoneInfoActivity>()

    }
}