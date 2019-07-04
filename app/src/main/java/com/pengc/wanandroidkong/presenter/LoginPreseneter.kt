package com.pengc.wanandroidkong.presenter

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.pengc.wanandroidkong.activity.LoginActivity
import com.pengc.wanandroidkong.bean.LoginInfo
import com.pengc.wanandroidkong.http.BaseResponse
import com.pengc.wanandroidkong.http.RetrofitUtil
import com.pengc.wanandroidkong.http.service.LoginService
import com.pengc.wanandroidkong.utils.CommonUtils
import com.pengc.wanandroidkong.utils.SpUtil
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginPreseneter: XPresent<LoginActivity>() {

    fun login(username: String, password: String) {
        v.showLoading(true)
        RetrofitUtil.create(LoginService::class.java).login(username,password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<LoginInfo>>{
                override fun onComplete() {
                    v.showLoading(false)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<LoginInfo>) {
                    if(t.isSuccess){
                        SpUtil.saveString(SpUtil.USER_NAME_KEY,username)
                        v.loginSucc()
                    }else{
                        v.loginFail(t.errorMsg)
                    }
                }

                override fun onError(e: Throwable) {
                    v.showLoading(false)
                    CommonUtils.showSnake(v.window.decorView, e.message.toString())
                }

            })

    }

    fun register(username: String, password: String, repassword: String) {
        v.showLoading(true)
        RetrofitUtil.create(LoginService::class.java).register(username,password,repassword)
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
                        v.registerSucc()
                    }else{
                        v.registerFail(t.errorMsg)
                    }
                }

                override fun onError(e: Throwable) {
                    CommonUtils.showSnake(v.window.decorView, e.message.toString())
                }

            })
    }
}