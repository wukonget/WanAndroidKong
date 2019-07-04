package com.pengc.wanandroidkong.bean

data class LoginInfo(
    var collectIds:List<Any>,
    var password:String,
    var icon:String,
    var username:String,
    var token:String,
    var nickname:String,
    var id:Long,
    var email:String,
    var chapterTops:List<Any>,
    var type:Int,
    var admin:Boolean
) {
}