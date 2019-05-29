package com.pengc.wanandroidkong.presenter

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.pengc.wan_main.mvp.model.entity.MainListData
import com.pengc.wanandroidkong.bean.BannerBean
import com.pengc.wanandroidkong.fragment.HomeFragment
import com.pengc.wanandroidkong.http.BaseResponse
import com.pengc.wanandroidkong.http.RetrofitUtil
import com.pengc.wanandroidkong.http.RetrofitUtil.Companion.logger
import com.pengc.wanandroidkong.http.service.MainService
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomePresenter:XPresent<HomeFragment>() {
    private var mLoadMoreListener: MainLoadMoreListener? = null
    private var mTotalPageCount: Int? = Int.MAX_VALUE
    private var mCurrentPageIndex: Int = 0

    fun loadBanner() {
        v.showLoading(true)
        RetrofitUtil.create(MainService::class.java).getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<List<BannerBean>>> {
                override fun onSubscribe(d: Disposable) {
                    logger.info("onSubscribe ${d.toString()}")
                }

                override fun onComplete() {
                    v.showLoading(false)
                }

                override fun onNext(t: BaseResponse<List<BannerBean>>) {
                    v.setBannerData(t.data)
                }

                override fun onError(e: Throwable) {
                    v.showText(e.message)
                }

            })
    }

    fun loadListData(pageIndex: Int, pullToRefresh: Boolean) {

        if(pullToRefresh) {
            v.showLoading(true)
        }
        mCurrentPageIndex = pageIndex
        if(mCurrentPageIndex >= mTotalPageCount!!){
            mLoadMoreListener?.loadMoreEnd()
        }
        RetrofitUtil.create(MainService::class.java).getMainList(pageIndex)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<MainListData>>{
                override fun onComplete() {
                    if(pullToRefresh) {
                        v.showLoading(false)
                    }
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<MainListData>) {
                    mTotalPageCount = t.data?.pageCount
                    v.setMainListData(t.data,pullToRefresh)
                    mLoadMoreListener?.loadMoreSuc()
                }

                override fun onError(e: Throwable) {
                    mLoadMoreListener?.loadMoreFail()
                }

            })
    }

    fun loadMore(mainLoadMoreListener: MainLoadMoreListener) {
        this.mLoadMoreListener = mainLoadMoreListener
        loadListData(++mCurrentPageIndex,false)
    }

    /**
     * 加载更多回调
     */
    interface MainLoadMoreListener{
        fun loadMoreEnd()
        fun loadMoreSuc()
        fun loadMoreFail()
    }
}