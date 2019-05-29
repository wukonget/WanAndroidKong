package com.pengc.wan_main.app.utils

import android.content.Context
import android.widget.ImageView
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.utils.GlideApp
import com.youth.banner.loader.ImageLoader

class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
        GlideApp.with(context!!).load(path).placeholder(R.drawable.loading).into(imageView!!)
    }
}