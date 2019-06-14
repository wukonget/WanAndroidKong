package com.pengc.wanandroidkong.utils

import com.pengc.wanandroidkong.base.WAKApplication

class CommonUtils {
    companion object{
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
    }
}