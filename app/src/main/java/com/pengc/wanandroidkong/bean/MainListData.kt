package com.pengc.wan_main.mvp.model.entity

data class MainListData(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: List<MainListItemData>
) {
}


data class MainListItemData(
    val apkLink: String,
    val author: String,
    val chapterId: String,
    val chapterName: String,
    val collect: Boolean,
    val courseId: String,
    val desc: String,
    val envelopePic: String,
    val fresh: Boolean,
    val id: String,
    val link: String,
    val niceDate: String,
    val origin: String,
    val prefix: String,
    val projectLink: String,
    val publishTime: String,
    val superChapterId: String,
    val superChapterName: String,
    val title: String,
    val type: String,
    val userId: String,
    val visible: String,
    val zan: Int,
    val tags: List<TagData>
) {}

data class TagData(
    val name: String,
    val url: String
) {}