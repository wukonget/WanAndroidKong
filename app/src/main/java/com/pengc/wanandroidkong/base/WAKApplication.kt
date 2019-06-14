package com.pengc.wanandroidkong.base

import android.app.Application
import android.content.Context

class WAKApplication:Application() {

    companion object{

        private var context:WAKApplication? = null

        fun getIns():Application{

            return context!!
        }
    }

    init {
        context = this
    }

}