package com.pengc.wanandroidkong.bean

import com.chad.library.adapter.base.entity.SectionEntity

class TodoListSection:SectionEntity<TodoListBean> {
    constructor(isHeader:Boolean,header:String):super(isHeader,header)
    constructor(t:TodoListBean):super(t)
}