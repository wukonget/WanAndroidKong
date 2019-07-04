package com.pengc.wanandroidkong.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.adapters.TodoListAdapter
import com.pengc.wanandroidkong.adapters.TodoListSecionAdapter
import com.pengc.wanandroidkong.base.BaseLazyFragment
import com.pengc.wanandroidkong.bean.TodoBean
import com.pengc.wanandroidkong.bean.TodoListBean
import com.pengc.wanandroidkong.presenter.TodoPresenter
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.fragment_todo.view.*

class TodoFragment:BaseLazyFragment<TodoPresenter>() {

    private val mTodoListAdapter = TodoListAdapter()
    private var mType:Int? = null//选择的类型  0,1,2,3
    private var priority:Int? = null//优先级
    private var orderby:Int = 4//排序方式，默认4    1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；
    private var status:Int = 0//状态：1-完成；0未完成; 默认全部展示；

    override fun showLoading(show: Boolean) {
        swipeRefreshLayout.isRefreshing = show
    }

    override fun initData(savedInstanceState: Bundle?) {
        initViews()
        p.loadTodoData(status,mType,priority,orderby,true)
    }

    private fun initViews() {
        todoType1.isSelected = true
        rootView.recyclerView.layoutManager = LinearLayoutManager(activity as Context?, RecyclerView.VERTICAL, false)
        rootView.recyclerView.adapter = mTodoListAdapter

        rootView.swipeRefreshLayout.setOnRefreshListener {
            p.loadTodoData(status,mType,priority,orderby,true)
        }

        rootView.setting.setOnClickListener {

            rootView.settingLayout.visibility = if(rootView.settingLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_todo
    }

    override fun newP(): TodoPresenter {
        return TodoPresenter()
    }

    fun setListData(data: List<TodoListBean>?, refresh: Boolean) {
        if(refresh){
            mTodoListAdapter.setNewData(data)
        }else{
            data?.let { mTodoListAdapter.addData(it) }
        }
    }
}