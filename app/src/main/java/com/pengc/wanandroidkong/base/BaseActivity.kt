package com.pengc.wanandroidkong.base

import android.os.Bundle
import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.XActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.pengc.wanandroidkong.R

abstract class BaseActivity<P : IPresent<*>> : XActivity<P>() {
    override fun initData(savedInstanceState: Bundle?) {
        immersionBar {
            navigationBarColor(R.color.colorAccent)
            statusBarColor(R.color.colorPrimary)
            autoStatusBarDarkModeEnable(true,0.2f)
            fitsSystemWindows(true)
        }
    }
}