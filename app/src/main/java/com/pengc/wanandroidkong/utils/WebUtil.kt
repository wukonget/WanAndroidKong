package com.pengc.wanandroidkong.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.pengc.wanandroidkong.activity.WebViewActivity

class WebUtil {

    companion object{
        fun loadUrl(activity: AppCompatActivity, url: String, title: String){
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.WEB_VIEW_URL_KEY,url)
            intent.putExtra(WebViewActivity.WEB_VIEW_TITLE_KEY,title)
            activity.startActivity(intent)
        }
    }

}