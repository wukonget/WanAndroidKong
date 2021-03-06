package com.pengc.wanandroidkong.presenter

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.pengc.wanandroidkong.bean.TodoResp
import com.pengc.wanandroidkong.fragment.TodoFragment
import com.pengc.wanandroidkong.http.BaseResponse
import com.pengc.wanandroidkong.http.RetrofitUtil
import com.pengc.wanandroidkong.http.service.TodoService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TodoPresenter : XPresent<TodoFragment>() {

    val todoService = RetrofitUtil.create(TodoService::class.java)
    var currentType:Int? = null
    var currentPage = 0

    fun loadTodoData(done:Int?, type: Int?, priority:Int?, orderby:Int, refresh: Boolean = true) {
        v.showLoading(true)
        if (refresh) {
            currentPage = 0
        } else {
            currentPage++
        }
        currentType = type
//        if(done) {
//            processData(todoService.getDoneData(type, page),refresh)
//        }else{
//            processData(todoService.getUndoneData(type, page),refresh)
//        }
        processData(todoService.getTodoData(currentPage,done,type,priority,orderby),refresh)
    }

    private fun processData(observable: Observable<BaseResponse<TodoResp>>, refresh:Boolean) {
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<TodoResp>> {
                override fun onComplete() {
                    v.showLoading(false)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<TodoResp>) {
                    if(t.isSuccess){
                        v.setListData(t.data?.datas,refresh)
                    }else{
                        v.showText("请求失败:${t.errorMsg}")
                    }
                }

                override fun onError(e: Throwable) {
                    v.showLoading(false)
                    v.showText("请求失败:${e.message}")
                }

            })
    }

    /**
     * 更改数据的状态  0未完成  1已完成
     */
    fun changeStatus(todoId: Long,position:Int,status:Int = 1) {

        if(todoId == 0L){
            return
        }
        v.showLoading(true)
        RetrofitUtil.create(TodoService::class.java)
            .changeStatus(todoId,status)
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
                        v.showText("修改状态成功")
                        v.changeStatusSucc(position,status)
                    }else{
                        v.showText("修改状态失败：${t.errorMsg.toString()}")
                    }
                }

                override fun onError(e: Throwable) {
                    v.showText("修改状态失败：${e.message.toString()}")
                }

            })
    }

    /**
     * 更改数据的状态  0未完成  1已完成
     */
    fun deleteTodo(todoId: Long,position:Int) {

        if(todoId == 0L){
            return
        }
        v.showLoading(true)
        RetrofitUtil.create(TodoService::class.java)
            .deleteTodoData(todoId)
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
                        v.showText("删除成功")
                        v.itemDeleteSucc(position)
                    }else{
                        v.showText("删除失败：${t.errorMsg.toString()}")
                    }
                }

                override fun onError(e: Throwable) {
                    v.showText("删除失败：${e.message.toString()}")
                }

            })
    }
}