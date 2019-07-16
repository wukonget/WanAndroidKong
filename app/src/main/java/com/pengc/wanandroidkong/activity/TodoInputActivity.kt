package com.pengc.wanandroidkong.activity

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.base.BaseActivity
import com.pengc.wanandroidkong.bean.TodoListBean
import com.pengc.wanandroidkong.presenter.TodoInputPresenter
import com.pengc.wanandroidkong.utils.TimeUtil
import kotlinx.android.synthetic.main.activity_todoinput.*
import java.text.SimpleDateFormat
import java.util.*

class TodoInputActivity : BaseActivity<TodoInputPresenter>() {

    private var mType: Int = 0//选择的类型  0,1,2,3
    private var mTypeView: TextView? = null
    private var mId : Long = 0

    companion object {
        public const val TODO_DATA_TAG = "todo_data_tag"
        public const val TODO_DATA_POSITION = "todo_data_position"
        public const val TODO_DATA_UPDATE = "todo_data_change_type"
        public const val TODO_INPUT_REQUEST_CODE = 10001
        public const val TODO_INPUT_RESPONSE_CODE = 10002
        fun launch(context: Context, todoData: TodoListBean) {
            val intent = Intent(context, TodoInputActivity::class.java)
            intent.putExtra(TODO_DATA_TAG, todoData)
            context.startActivity(intent)
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initViews()
    }

    override fun beforeSetContent() {
        super.beforeSetContent()
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
    }

    private fun initViews() {
        val todoData = intent.getParcelableExtra<TodoListBean>(TODO_DATA_TAG)
        if(todoData == null){
            page_title.text = "新建TODO"
            todo_title.setText("")
            todo_content.setText("")
            todo_date.text = "开始日期： ${SimpleDateFormat("yyyy-MM-dd").format(Date())}"
            btn_right.text = "确定"
            btn_right.tag = 0
            btn_left.text = "取消"
            btn_left.setBackgroundColor(resources.getColor(R.color.colorPrimary))
            btn_left.setOnClickListener { finish() }
        }else{
            mId = todoData.id
            page_title.text = "编辑TODO"
            todo_title.setText(todoData.title)
            todo_content.setText(todoData.content)
            todo_date.text = "开始日期： ${todoData.dateStr}"
            todo_priority.setText(todoData.priority.toString())
            btn_right.text = "更新"
            btn_right.tag = 1
            btn_left.text = "完成"
            btn_left.setBackgroundColor(resources.getColor(R.color.colorAccent))
            btn_left.setOnClickListener {
                //修改todo状态
                p.changeStatus(mId,getDataPosition())
            }

            bottomLayout.visibility = if(todoData.status==0) View.VISIBLE else View.GONE

            setTodoType(todoData.type)
        }

        todoType1.isSelected = true
        todoType1.setOnClickListener(typeClickListener)
        todoType2.setOnClickListener(typeClickListener)
        todoType3.setOnClickListener(typeClickListener)
        todoType4.setOnClickListener(typeClickListener)

        btn_right.setOnClickListener {
            if(it.tag.toString().toInt() == 0){
                //新增
                p.saveTodo(getTodoData())
            }else{
                //编辑更新
                p.updateTodo(getTodoData(mId),getDataPosition())
            }
        }

        todo_date.setOnClickListener {
            val cal = Calendar.getInstance()
            DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    todo_date.text = "开始日期： $year-${if(month<9) "0${month+1}" else (month+1)}-${if(dayOfMonth<10) "0$dayOfMonth" else dayOfMonth}"
                },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        todo_input_back.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setTodoType(type: Int) {
        mType = type
        when(type){
            0->{
                mTypeView = todoType1
            }
            1->{
                mTypeView = todoType2
            }
            2->{
                mTypeView = todoType3
            }
            3->{
                mTypeView = todoType4
            }
        }
    }

    private fun getDataPosition(): Int {
        return intent.getIntExtra(TODO_DATA_POSITION,-1)
    }

    private fun getTodoData(id:Long = 0): TodoListBean? {
        val title = todo_title.text.toString().trim()
        val content = todo_content.text.toString().trim()
        val dateStr = todo_date.text.toString().split("：")[1].trim()
        val date = TimeUtil.getLongFromStringyyyyMMdd(dateStr)
        val priority = if(todo_priority.text.toString().isEmpty()) 0 else todo_priority.text.toString().toInt()
        if(title.isEmpty()){
            showMessage("验证失败：标题不能为空")
            return null
        }
        if(content.isEmpty()){
            showMessage("验证失败：内容不能为空")
            return null
        }
        if(dateStr.isEmpty()){
            showMessage("验证失败：开始日期不能为空")
            return null
        }

        return TodoListBean(0,null,content,date,dateStr,id,0,priority,title, mType,0)
    }

    override fun showLoading(show: Boolean) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_todoinput
    }

    override fun newP(): TodoInputPresenter {
        return TodoInputPresenter()
    }

    fun itemChangeSucc(position: Int,isUpdate:Boolean) {
        val intent = Intent()
        intent.putExtra(TODO_DATA_POSITION,position)
        intent.putExtra(TODO_DATA_UPDATE,isUpdate)
        setResult(TODO_INPUT_RESPONSE_CODE,intent)
    }


    private val typeClickListener = View.OnClickListener { v ->
        mTypeView?.setTextColor(Color.BLACK)
        if (mTypeView == v) {
            mTypeView = null
            mType = 0
        } else {
            mType = (v?.tag).toString().toInt()
            mTypeView = v as TextView?
            mTypeView?.setTextColor(resources.getColor(R.color.colorPrimary))
        }

    }
}