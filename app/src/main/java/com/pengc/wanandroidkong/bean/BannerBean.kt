package com.pengc.wanandroidkong.bean

/**
 * 首页Banner图
 */

data class BannerBean(
    var desc:String,//描述
    var id: Long,
    var imagePath: String,//图片路径
    var isVisible: Int,
    var order: Int,
    var title: String,//标题
    var type: Int,
    var url: String
) {
}