package com.pengc.wanandroidkong.presenter

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.pengc.wanandroidkong.bean.NavTypeBean
import com.pengc.wanandroidkong.fragment.NavFragment
import com.pengc.wanandroidkong.http.BaseResponse
import com.pengc.wanandroidkong.http.RetrofitUtil
import com.pengc.wanandroidkong.http.service.NavService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class NavPresenter:XPresent<NavFragment>() {

    fun loadData() {
        v.showLoading(true)
        RetrofitUtil.create(NavService::class.java).getNavData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<List<NavTypeBean>>> {
                override fun onSubscribe(d: Disposable) {
                    RetrofitUtil.logger.info("onSubscribe $d")
                }

                override fun onComplete() {
                    v.showLoading(false)
                }

                override fun onNext(t: BaseResponse<List<NavTypeBean>>) {
                    v.setNavData(t.data)
                }

                override fun onError(e: Throwable) {
                    v.showText(e.message)
                }

            })
    }

}