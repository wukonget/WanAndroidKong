package com.pengc.wanandroidkong.base

import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.XLazyFragment
import com.blankj.utilcode.util.SnackbarUtils
import com.google.android.material.snackbar.Snackbar
import com.pengc.wanandroidkong.utils.CommonUtils

abstract class BaseLazyFragment<P:IPresent<*>> : XLazyFragment<P>() {
    abstract fun showLoading(show: Boolean)
    fun showText(message: String?){
        CommonUtils.showSnake(rootView,message.toString())
    }

    open fun onActivityBackPressed():Boolean{
        return false
    }
}