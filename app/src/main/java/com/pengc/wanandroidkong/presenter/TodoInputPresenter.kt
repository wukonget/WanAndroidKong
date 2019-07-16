package com.pengc.wanandroidkong.presenter

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.pengc.wanandroidkong.activity.TodoInputActivity
import com.pengc.wanandroidkong.bean.TodoListBean
import com.pengc.wanandroidkong.http.BaseResponse
import com.pengc.wanandroidkong.http.RetrofitUtil
import com.pengc.wanandroidkong.http.service.TodoService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TodoInputPresenter : XPresent<TodoInputActivity>() {

    /**
     * 新增一个todo
     */
    fun saveTodo(todoData: TodoListBean?) {
        if(todoData == null){
            return
        }
        v.showLoading(true)
        RetrofitUtil.create(TodoService::class.java)
            .addTodoData(todoData.title,todoData.content,todoData.dateStr,todoData.type,todoData.priority)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<String>>{
                override fun onComplete() {
                    v.showLoading(false)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<String>) {
                    if(t.isSuccess){
                        v.showMessage("保存成功")
                        v.backPressDelay(1000)
                    }else{
                        v.showMessage("保存失败：${t.errorMsg.toString()}")
                    }
                }

                override fun onError(e: Throwable) {
                    v.showMessage("保存失败：${e.message.toString()}")
                }

            })
    }

    /**
     * 更新一个todo
     */
    fun updateTodo(todoData: TodoListBean?,position: Int = -1) {
        if(todoData == null){
            return
        }
        v.showLoading(true)
        RetrofitUtil.create(TodoService::class.java)
            .updateTodoData(todoData.id,todoData.title,todoData.content,todoData.dateStr,todoData.type,todoData.priority)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<String>>{
                override fun onComplete() {
                    v.showLoading(false)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<String>) {
                    if(t.isSuccess){
                        v.showMessage("更新保存成功")
                        v.itemChangeSucc(position,true)
                        v.backPressDelay(1000)
                    }else{
                        v.showMessage("更新保存失败：${t.errorMsg.toString()}")
                    }
                }

                override fun onError(e: Throwable) {
                    v.showMessage("更新保存失败：${e.message.toString()}")
                }

            })
    }

    fun changeStatus(mId: Long, position: Int) {


        if(mId == 0L){
            return
        }
        v.showLoading(true)
        RetrofitUtil.create(TodoService::class.java)
            .changeStatus(mId,1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<String>>{
                override fun onComplete() {
                    v.showLoading(false)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<String>) {
                    if(t.isSuccess){
                        v.showMessage("修改状态成功")
                        v.itemChangeSucc(position,false)
                        v.backPressDelay(1000)
                    }else{
                        v.showMessage("修改状态失败：${t.errorMsg.toString()}")
                    }
                }

                override fun onError(e: Throwable) {
                    v.showMessage("修改状态失败：${e.message.toString()}")
                }

            })
    }

}