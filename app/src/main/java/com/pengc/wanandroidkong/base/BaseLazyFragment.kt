package com.pengc.wanandroidkong.base

import android.content.Context
import android.os.Bundle
import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.XLazyFragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseLazyFragment<P:IPresent<*>> : XLazyFragment<P>() {
    abstract fun showLoading(show: Boolean)
    fun showText(message: String?){
        Snackbar.make(rootView, message.toString(), Snackbar.LENGTH_SHORT).show()
    }
}