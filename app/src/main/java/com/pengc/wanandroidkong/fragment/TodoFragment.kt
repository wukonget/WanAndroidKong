package com.pengc.wanandroidkong.fragment

import android.app.ActivityOptions
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.activity.TodoInputActivity
import com.pengc.wanandroidkong.adapters.TodoListAdapter
import com.pengc.wanandroidkong.base.BaseLazyFragment
import com.pengc.wanandroidkong.bean.TodoListBean
import com.pengc.wanandroidkong.presenter.TodoPresenter
import kotlinx.android.synthetic.main.fragment_todo.*
import kotlinx.android.synthetic.main.fragment_todo.view.*

class TodoFragment : BaseLazyFragment<TodoPresenter>() {

    private val mTodoListAdapter = TodoListAdapter()
    private var mType: Int? = null//选择的类型  0,1,2,3
    private var mTypeView: TextView? = null
    private var priority: Int? = null//优先级
    private var orderby: Int = 4//排序方式，默认4    1:完成日期顺序；2.完成日期逆序；3.创建日期顺序；4.创建日期逆序(默认)；
    private var orderbyView: TextView? = null
    private var status: Int? = null//状态：1-完成；0未完成; 默认全部展示；
    private var statusView: TextView? = null

    override fun showLoading(show: Boolean) {
        swipeRefreshLayout.isRefreshing = show
    }

    override fun initData(savedInstanceState: Bundle?) {
        initViews()
        reloadData()
    }

    private fun initViews() {
        rootView.recyclerView.layoutManager = LinearLayoutManager(activity as Context?, RecyclerView.VERTICAL, false)
        mTodoListAdapter.setOnItemClickListener { adapter, view, position ->
            toTodoInput(view,adapter.data[position] as TodoListBean,position)
        }

        mTodoListAdapter.setOnItemChildClickListener { adapter, view, position ->
            val todoId = (adapter.data[position] as TodoListBean).id
            when(view.id){
                R.id.btn_done->{
                    AlertDialog.Builder(activity as Context)
                        .setTitle("更改为完成状态吗")
                        .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->
                            p.changeStatus(todoId,position)
                        }).setNegativeButton("取消",null).show()
                }
                R.id.btn_delete->{
                    AlertDialog.Builder(activity as Context)
                        .setTitle("删除这条TODO吗")
                        .setPositiveButton("确定", DialogInterface.OnClickListener { dialog, which ->
                            p.deleteTodo(todoId,position)
                        }).setNegativeButton("取消",null).show()
                }
            }
        }
        rootView.recyclerView.adapter = mTodoListAdapter

        rootView.swipeRefreshLayout.setOnRefreshListener {
            reloadData()
        }

        rootView.setting.setOnClickListener {

            rootView.settingLayout.visibility =
                if (rootView.settingLayout.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }

        rootView.todoType1.setOnClickListener(typeClickListener)
        rootView.todoType2.setOnClickListener(typeClickListener)
        rootView.todoType3.setOnClickListener(typeClickListener)
        rootView.todoType4.setOnClickListener(typeClickListener)

        rootView.todoOrder4.isSelected = true
        orderbyView = rootView.todoOrder4
        rootView.todoOrder1.setOnClickListener(orderClickListener)
        rootView.todoOrder2.setOnClickListener(orderClickListener)
        rootView.todoOrder3.setOnClickListener(orderClickListener)
        rootView.todoOrder4.setOnClickListener(orderClickListener)

        rootView.todoStatus1.setOnClickListener(statusClickListener)
        rootView.todoStatus2.setOnClickListener(statusClickListener)

        rootView.filter.setOnClickListener {
            reloadData()
        }

        rootView.addTodo.setOnClickListener {
            toTodoInput(addTodo,null)
        }
    }

    private fun reloadData() {
        p.loadTodoData(status, mType, priority, orderby, true)
    }

    private fun toTodoInput(view:View,bean:TodoListBean?,position:Int = -1) {
        val intent = Intent(activity, TodoInputActivity::class.java)
        intent.putExtra(TodoInputActivity.TODO_DATA_TAG, bean)
        intent.putExtra(TodoInputActivity.TODO_DATA_POSITION, position)
        startActivityForResult(
            intent,
            TodoInputActivity.TODO_INPUT_REQUEST_CODE,
            ActivityOptions.makeSceneTransitionAnimation(
                activity,
                view,
                getString(R.string.todoInputTrans)
            ).toBundle()
        )
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_todo
    }

    override fun newP(): TodoPresenter {
        return TodoPresenter()
    }

    fun setListData(data: List<TodoListBean>?, refresh: Boolean) {
        if (refresh) {
            mTodoListAdapter.setNewData(data)
        } else {
            data?.let { mTodoListAdapter.addData(it) }
        }
    }

    /**
     * 修改状态成功
     */
    fun changeStatusSucc(position: Int, status:Int) {
        mTodoListAdapter.data[position].status = status
        mTodoListAdapter.notifyItemChanged(position)
    }

    /**
     * 删除成功
     */
    fun itemDeleteSucc(position: Int) {
        mTodoListAdapter.remove(position)
    }

    private val typeClickListener = View.OnClickListener { v ->
        mTypeView?.setTextColor(Color.BLACK)
        if (mTypeView == v) {
            mTypeView = null
            mType = null
        } else {
            mType = (v?.tag).toString().toInt()
            mTypeView = v as TextView?
            mTypeView?.setTextColor(resources.getColor(R.color.colorPrimary))
        }

    }

    private val orderClickListener = View.OnClickListener { v ->
        orderbyView?.setTextColor(Color.BLACK)
        orderby = (v?.tag).toString().toInt()
        orderbyView = v as TextView?
        orderbyView?.setTextColor(resources.getColor(R.color.colorPrimary))
    }

    private val statusClickListener = View.OnClickListener { v ->
        statusView?.setTextColor(Color.BLACK)
        if (statusView == v) {
            statusView = null
            status = null
        } else {
            status = (v?.tag).toString().toInt()
            statusView = v as TextView?
            statusView?.setTextColor(resources.getColor(R.color.colorPrimary))
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == TodoInputActivity.TODO_INPUT_REQUEST_CODE && resultCode == TodoInputActivity.TODO_INPUT_RESPONSE_CODE){
            if(data!=null){
                val position = data.getIntExtra(TodoInputActivity.TODO_DATA_POSITION,-1)
                val isUpdate = data.getBooleanExtra(TodoInputActivity.TODO_DATA_UPDATE,true)
                if(position >= 0 && !isUpdate){
                        changeStatusSucc(position, 1)
                }else{
                    reloadData()
                }
            }
        }
    }
}