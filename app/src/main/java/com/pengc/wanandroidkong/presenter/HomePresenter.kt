package com.pengc.wanandroidkong.presenter

import cn.droidlover.xdroidmvp.mvp.XPresent
import com.pengc.wan_main.mvp.model.entity.MainListData
import com.pengc.wanandroidkong.bean.BannerBean
import com.pengc.wanandroidkong.bean.HotSearchKey
import com.pengc.wanandroidkong.bean.TixiBean
import com.pengc.wanandroidkong.fragment.HomeFragment
import com.pengc.wanandroidkong.http.BaseResponse
import com.pengc.wanandroidkong.http.RetrofitUtil
import com.pengc.wanandroidkong.http.RetrofitUtil.Companion.logger
import com.pengc.wanandroidkong.http.service.MainService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomePresenter:XPresent<HomeFragment>() {
    private var mCid: Int = -1
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

    fun loadListData(pageIndex: Int, pullToRefresh: Boolean,cid:Int = -1) {
        mCid = cid
        if(pullToRefresh) {
            v.showLoading(true)
        }
        mCurrentPageIndex = pageIndex
        if(mCurrentPageIndex >= mTotalPageCount!!){
            mLoadMoreListener?.loadMoreEnd()
            return
        }

        val ob : Observable<BaseResponse<MainListData>> = if(cid == -1){
            RetrofitUtil.create(MainService::class.java).getMainList(pageIndex)
        }else{
            RetrofitUtil.create(MainService::class.java).getTixiDataList(pageIndex,cid)
        }
        ob.subscribeOn(Schedulers.io())
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
        loadListData(++mCurrentPageIndex,false,mCid)
    }

    fun loadSearchKeys() {
        v.showLoading(true)
        RetrofitUtil.create(MainService::class.java).getSearchKeys()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<List<HotSearchKey>>>{
                override fun onComplete() {
                    v.showLoading(false)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<List<HotSearchKey>>) {
                    v.setSearchKeys(t.data)
                }

                override fun onError(e: Throwable) {
                    v.showText(e.message)
                }

            })
    }

    fun loadTixi() {
        v.showLoading(true)
        RetrofitUtil.create(MainService::class.java).getTixi()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<BaseResponse<List<TixiBean>>>{
                override fun onComplete() {
                    v.showLoading(false)
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: BaseResponse<List<TixiBean>>) {
                    val res = t.data as ArrayList<TixiBean>
                    val allItemChildrenList = ArrayList<TixiBean>()
                    val allItemChildren = TixiBean(-1,-1,"所有",-1,-1,false,1,ArrayList<TixiBean>())
                    allItemChildrenList.add(allItemChildren)
                    val allItem = TixiBean(-1,-1,"所有",-1,-1,false,1,allItemChildrenList)
                    res.add(0,allItem)//添加默认的  所有/所有  item
                    v.setTixiData(res)
                }

                override fun onError(e: Throwable) {
                    v.showText(e.message)
                }

            })
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