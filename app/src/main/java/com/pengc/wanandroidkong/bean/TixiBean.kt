package com.pengc.wanandroidkong.bean

data class TixiBean(
    val courseId: Int,
    val id: Int,
    val name: String,
    val order: Int,
    val parentChapterId: Int,
    val userControlSetTop: Boolean,
    val visible: Int,
    val children: List<TixiBean>
) {
}
