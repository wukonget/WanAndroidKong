package com.pengc.wanandroidkong.presenter

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.pengc.wanandroidkong.bean.LoginInfo
import com.pengc.wanandroidkong.fragment.MeFragment
import com.pengc.wanandroidkong.http.BaseResponse
import com.pengc.wanandroidkong.http.RetrofitUtil
import com.pengc.wanandroidkong.http.service.LoginService
import com.pengc.wanandroidkong.utils.CommonUtils
import com.pengc.wanandroidkong.utils.SpUtil
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MePresenter:XPresent<MeFragment>() {
    fun logout() {
        v.showLoading(true)
        RetrofitUtil.create(LoginService::class.java).logout()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<String>> {
                override fun onComplete() {
                    v.showLoading(false)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<String>) {
                    if(t.isSuccess){
                        SpUtil.clearSp()
                        v.logoutSucc()
                    }else{
                        CommonUtils.showSnake(v.view!!, t.errorMsg.toString())
                    }
                }

                override fun onError(e: Throwable) {
                    v.showLoading(false)
                    CommonUtils.showSnake(v.view!!, e.message.toString())
                }

            })
    }
}