package com.pengc.wanandroidkong.utils

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.pengc.wanandroidkong.activity.WebViewActivity

class WebUtil {

    companion object{
        fun loadUrl(activity: AppCompatActivity, url: String, title: String){
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.WEBVIEWURLKEY,url)
            intent.putExtra(WebViewActivity.WEBVIEWTITLEKEY,title)
            activity.startActivity(intent)
        }
    }

}