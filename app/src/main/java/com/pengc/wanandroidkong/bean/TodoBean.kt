package com.pengc.wanandroidkong.bean

data class TodoBean(
    var date: Long,
    var todoList: List<TodoListBean>
) {}


data class TodoListBean(
    var completeDate: Long,
    var completeDateStr: String,
    var content: String,
    var date: Long,
    var dateStr: String,
    var id: Int,
    var status: Int,
    var title: String,
    var type: Int,
    var userId: Int
) {}

data class TodoRespSection(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: List<TodoBean>
){}

data class TodoResp(
    var curPage: Int,
    var offset: Int,
    var over: Boolean,
    var pageCount: Int,
    var size: Int,
    var total: Int,
    var datas: List<TodoListBean>
){}

