package com.pengc.wanandroidkong.adapters

import android.text.format.DateUtils
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.base.WAKApplication
import com.pengc.wanandroidkong.bean.TodoBean
import com.pengc.wanandroidkong.bean.TodoListSection

class TodoListSecionAdapter:BaseSectionQuickAdapter<TodoListSection,BaseViewHolder>(R.layout.item_todo_item,R.layout.item_todo_date,ArrayList<TodoListSection>()) {

//    private val headerPosition = HashMap<String,Int>()

    override fun convertHead(helper: BaseViewHolder?, item: TodoListSection?) {
        helper?.setText(R.id.headerName,item?.header)
    }

    override fun convert(helper: BaseViewHolder?, item: TodoListSection?) {
//        helper?.getView<LinearLayout>(R.id.back)?.setBackgroundResource(R.color.white)
        helper?.setText(R.id.title,item?.t?.title)
        helper?.setText(R.id.content,item?.t?.content)
    }

    fun setNewTodoData(data: List<TodoBean>?) {
        val sectionData = getSectionData(data)
        setNewData(sectionData)
    }

    fun addTodoData(data:List<TodoBean>?){
        val sectionData = getSectionData(data)
        addData(sectionData)
    }

    private fun getSectionData(data: List<TodoBean>?): ArrayList<TodoListSection> {
        val sectionData = ArrayList<TodoListSection>()
        data?.forEach { it ->
            sectionData.add(
                TodoListSection(
                    true,
                    DateUtils.formatDateTime(
                        WAKApplication.getIns().applicationContext,
                        it.date,
                        DateUtils.FORMAT_SHOW_DATE
                    )
                )
            )
            it.todoList.forEach { it1 ->
                sectionData.add(TodoListSection(it1))
            }
        }
        return sectionData
    }

//    fun getHeaderPosition(name:String):Int{
//        return if (headerPosition[name] ==null) 0 else (headerPosition[name]!!)
//    }
}