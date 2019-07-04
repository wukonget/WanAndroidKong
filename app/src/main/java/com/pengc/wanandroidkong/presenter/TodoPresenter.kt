package com.pengc.wanandroidkong.presenter

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.pengc.wanandroidkong.bean.TodoResp
import com.pengc.wanandroidkong.bean.TodoRespSection
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
}