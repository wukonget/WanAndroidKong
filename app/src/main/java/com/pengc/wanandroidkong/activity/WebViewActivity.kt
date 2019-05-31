package com.pengc.wanandroidkong.activity

import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.just.agentweb.AgentWeb
import com.pengc.wanandroidkong.R
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {

    private var mAgentWeb: AgentWeb? = null

    companion object{
        public val WEBVIEWURLKEY = "webViewUrl"
        public val WEBVIEWTITLEKEY = "webViewTitle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        back_arrow.setOnClickListener { onBackPressed() }
        openWebView()
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
       return intent.getStringExtra(WEBVIEWURLKEY)
    }

    private fun getWebTitle():String?{
        return intent.getStringExtra(WEBVIEWTITLEKEY)
    }

    override fun onBackPressed() {
        if (mAgentWeb==null || !mAgentWeb!!.back()){
            finish()
        }
    }

}