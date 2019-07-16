package com.pengc.wanandroidkong.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.bean.TodoListBean

class TodoListAdapter : BaseQuickAdapter<TodoListBean,BaseViewHolder>(R.layout.item_todo_item,ArrayList<TodoListBean>()) {
    override fun convert(helper: BaseViewHolder?, item: TodoListBean?) {

        helper?.setText(R.id.title,item?.title)
        helper?.setText(R.id.content,item?.content)
        helper?.setText(R.id.date,item?.dateStr)

        helper?.addOnClickListener(R.id.btn_done)?.addOnClickListener(R.id.btn_delete)

        helper?.getView<ImageView>(R.id.doneIcon)?.visibility = if(item?.status == 0) View.INVISIBLE else View.VISIBLE
        helper?.getView<TextView>(R.id.btn_done)?.visibility = if(item?.status == 0) View.VISIBLE else View.INVISIBLE
    }
}