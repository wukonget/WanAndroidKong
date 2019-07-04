package com.pengc.wanandroidkong.adapters

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.bean.TodoListBean

class TodoListAdapter : BaseQuickAdapter<TodoListBean,BaseViewHolder>(R.layout.item_todo_item,ArrayList<TodoListBean>()) {
    override fun convert(helper: BaseViewHolder?, item: TodoListBean?) {

        helper?.setText(R.id.title,item?.title)
        helper?.setText(R.id.content,item?.content)
        helper?.setText(R.id.date,item?.dateStr)

    }
}