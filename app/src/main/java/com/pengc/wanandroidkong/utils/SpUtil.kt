package com.pengc.wanandroidkong.utils

import android.content.Context
import android.content.SharedPreferences
import com.pengc.wanandroidkong.base.WAKApplication

class SpUtil {
    companion object{

        public val USER_NAME_KEY = "user_name"

        public fun getDefaultSp():SharedPreferences{
            return WAKApplication.getIns().getSharedPreferences("wak", Context.MODE_PRIVATE)
        }

        public fun saveString(name:String,value:String){
            getDefaultSp().edit().putString(name,value).apply()
        }

        public fun getString(name: String):String?{
            return getDefaultSp().getString(name,null)
        }

        public fun clearSp(){
            getDefaultSp().edit().clear().apply()
        }

    }
}