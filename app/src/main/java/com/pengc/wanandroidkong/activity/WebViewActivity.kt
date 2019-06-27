package com.pengc.wanandroidkong.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.just.agentweb.AgentWeb
import com.pengc.wanandroidkong.R
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {

    private var mAgentWeb: AgentWeb? = null

    companion object{
        const val WEB_VIEW_URL_KEY = "webViewUrl"
        const val WEB_VIEW_TITLE_KEY = "webViewTitle"

        fun launch(context: Context, url:String, title:String){
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(WebViewActivity.WEB_VIEW_URL_KEY,url)
            intent.putExtra(WebViewActivity.WEB_VIEW_TITLE_KEY,title)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        back_arrow.setOnClickListener { onBackPressed() }
        openWebView()
        immersionBar {
            navigationBarColor(R.color.colorAccent)
            statusBarColor(R.color.colorPrimary)
            autoStatusBarDarkModeEnable(true,0.2f)
            fitsSystemWindows(true)
        }
    }

    private fun openWebView() {
        mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(webViewContainer, ViewGroup.LayoutParams(-1,-1))
            .useDefaultIndicator()
            .createAgentWeb()
            .ready()
            .go(getUrl())
        web_title.text = getWebTitle()
        web_title.isSelected = true
    }

    private fun getUrl(): String? {
       return intent.getStringExtra(WEB_VIEW_URL_KEY)
    }

    private fun getWebTitle():String?{
        return intent.getStringExtra(WEB_VIEW_TITLE_KEY)
    }

    override fun onBackPressed() {
        if (mAgentWeb==null || !mAgentWeb!!.back()){
            finish()
        }
    }

}