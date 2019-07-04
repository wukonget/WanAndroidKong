package com.pengc.wanandroidkong.utils

import android.annotation.SuppressLint
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.SnackbarUtils
import com.google.android.material.snackbar.Snackbar
import com.pengc.wanandroidkong.base.WAKApplication

class CommonUtils {
    companion object{
        @SuppressLint("StaticFieldLeak")
        var snackbar:Snackbar?= null

        /**
         * 将dp转换成px
         * @param dpValue
         * @return
         */
        fun dp2px(dpValue : Float) : Int{
            val scale = WAKApplication.getIns().applicationContext.resources.displayMetrics.density
            return (dpValue * scale + 0.5f).toInt()
        }

        /**
         * 将像素转换成dp
         * @param pxValue
         * @return
         */
        fun px2dp(pxValue:Float) : Int{
            val scale = WAKApplication.getIns().applicationContext.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }

        fun showSnake(view: View, message:String){
            val s = SnackbarUtils.with(view).setMessage(message)
            when {
                message.contains("成功") -> s.showSuccess()
                message.contains("失败") -> {
                    s.showError()
                    LogUtils.e(message)
                }
                else -> s.show()
            }
        }
    }
}