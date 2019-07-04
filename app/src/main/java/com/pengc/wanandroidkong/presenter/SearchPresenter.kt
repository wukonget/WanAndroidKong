package com.pengc.wanandroidkong.presenter

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.pengc.wanandroidkong.activity.SearchActivity
import com.pengc.wanandroidkong.bean.MainListData
import com.pengc.wanandroidkong.http.BaseResponse
import com.pengc.wanandroidkong.http.RetrofitUtil
import com.pengc.wanandroidkong.http.service.SearchService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class SearchPresenter:XPresent<SearchActivity>() {

    private var mLoadMoreListener: SearchLoadMoreListener?=null
    private var mTotalPageCount: Int? = Int.MAX_VALUE
    private var mCurrentPageIndex: Int = 0
    private var mCurrentQueryText: String = ""

    fun loadSearchData(pageIndex: Int, pullToRefresh: Boolean, queryText: String) {

        mCurrentQueryText = queryText
        if(pullToRefresh) {
            v.showLoading(true)
        }
        mCurrentPageIndex = pageIndex
        if(mCurrentPageIndex >= mTotalPageCount!!){
            mLoadMoreListener?.loadMoreEnd()
            return
        }

        val ob : Observable<BaseResponse<MainListData>> =
            RetrofitUtil.create(SearchService::class.java).querySearch(pageIndex,mCurrentQueryText)

        ob.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<MainListData>> {
                override fun onComplete() {
                    if(pullToRefresh) {
                        v.showLoading(false)
                    }
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<MainListData>) {
                    mTotalPageCount = t.data?.pageCount
                    v.setSearchListData(t.data,pullToRefresh)
                    mLoadMoreListener?.loadMoreSuc()
                }

                override fun onError(e: Throwable) {
                    v.showLoading(false)
                    mLoadMoreListener?.loadMoreFail()
                }

            })
    }

    fun loadMore(mainLoadMoreListener: SearchLoadMoreListener) {
        this.mLoadMoreListener = mainLoadMoreListener
        loadSearchData(++mCurrentPageIndex,false,mCurrentQueryText)
    }

    /**
     * 加载更多回调
     */
    interface SearchLoadMoreListener{
        fun loadMoreEnd()
        fun loadMoreSuc()
        fun loadMoreFail()
    }
}