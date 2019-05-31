package com.pengc.wanandroidkong.bean

data class MainListData(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: List<MainListItemData>
) {
}


data class MainListItemData(
    var apkLink: String,
    var author: String,
    var chapterId: String,
    var chapterName: String,
    var collect: Boolean,
    var courseId: String,
    var desc: String,
    var envelopePic: String,
    var fresh: Boolean,
    var id: String,
    var link: String,
    var niceDate: String,
    var origin: String,
    var prefix: String,
    var projectLink: String,
    var publishTime: String,
    var superChapterId: String,
    var superChapterName: String,
    var title: String,
    var type: String,
    var userId: String,
    var visible: String,
    var zan: Int,
    var tags: List<TagData>
) {}

data class TagData(
    var name: String,
    var url: String
) {}