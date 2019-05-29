package com.pengc.wanandroidkong.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pengc.wan_main.app.utils.GlideImageLoader
import com.pengc.wan_main.mvp.model.entity.MainListData
import com.pengc.wan_main.mvp.ui.adapter.MainListAdapter
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.base.BaseLazyFragment
import com.pengc.wanandroidkong.bean.BannerBean
import com.pengc.wanandroidkong.presenter.HomePresenter
import com.pengc.wanandroidkong.presenter.MainPresenter
import com.pengc.wanandroidkong.utils.WebUtil
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment:BaseLazyFragment<HomePresenter>() {
    private var windowSize: Double = 0.0
    private lateinit var mMainListAdapter: MainListAdapter
    private var loadingCount = 0

    private fun loadData() {
        p.loadBanner()
        p.loadListData(0,true)
    }

    private fun initRecycler() {
        rootView.swipeRefresh.setColorSchemeResources(R.color.colorAccent)
        rootView.swipeRefresh.setOnRefreshListener {
            p.loadListData(0, true)
        }
        mMainListAdapter = MainListAdapter()
        mMainListAdapter.setOnLoadMoreListener({
            p.loadMore(object : HomePresenter.MainLoadMoreListener {
                override fun loadMoreEnd() {
                    mMainListAdapter.loadMoreEnd()
                }

                override fun loadMoreSuc() {
                    mMainListAdapter.loadMoreComplete()
                }

                override fun loadMoreFail() {
                    mMainListAdapter.loadMoreFail()
                }

            })
        }, rootView.recyclerView)
        mMainListAdapter.setOnItemClickListener { adapter, _, position -> WebUtil.loadUrl(activity as AppCompatActivity,(adapter as MainListAdapter).data[position].link) }
        rootView.recyclerView.adapter = mMainListAdapter
        rootView.recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initBanner()
        initRecycler()
        initFab()
        loadData()
    }

    private fun initFab() {
        rootView.fab_search.setOnClickListener {
            showSearchPop(true)
        }
        rootView.back_search.setOnClickListener {
            showSearchPop(false)
        }
    }

    private fun initBanner() {
        rootView.banner.setImageLoader(GlideImageLoader())
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun newP(): HomePresenter {
        return HomePresenter()
    }

    override fun showLoading(show: Boolean) {
        if(show) loadingCount++ else loadingCount--
        rootView.swipeRefresh?.isRefreshing = loadingCount>0
    }

    fun setBannerData(bannerData: List<BannerBean>?) {
        val images = ArrayList<String>()
        bannerData?.forEach { images.add(it.imagePath) }
        rootView.banner.setImages(images)
        rootView.banner.setOnBannerListener { position ->
            bannerData?.get(position)?.url?.let { WebUtil.loadUrl(activity as AppCompatActivity, it) }
        }
        rootView.banner.start()
        rootView.banner.startAutoPlay()
    }

    fun setMainListData(data: MainListData?, pullToRefresh: Boolean) {
        if(pullToRefresh) {
            mMainListAdapter.setNewData(data?.datas)
        }else{
            data?.datas?.let { mMainListAdapter.addData(it) }
        }
    }

    private fun initWindowSize() {
        val width = if(activity == null) 0 else activity!!.windowManager.defaultDisplay.width
        val height = if(activity == null) 0 else activity!!.windowManager.defaultDisplay.height
        windowSize = Math.sqrt((width*width+height*height).toDouble())
    }

    private fun showSearchPop(b: Boolean) {
        var startR = 0.0F
        var endR = 0.0F
        if (b) {
            rootView.banner.stopAutoPlay()
            startR = (rootView.fab_search.width / 2).toFloat()
            endR = windowSize.toFloat()
        } else {
            rootView.banner.startAutoPlay()
            endR = (rootView.fab_search.width / 2).toFloat()
            startR = windowSize.toFloat()
        }
        val closeAnim = ViewAnimationUtils.createCircularReveal(
            rootView.av, (rootView.fab_search.x + rootView.fab_search.width / 2).toInt(), (rootView.fab_search.y + rootView.fab_search.height / 2).toInt(),
            startR, endR
        )
        closeAnim.duration = 600
        closeAnim.addListener(object : AnimatorListenerAdapter(){
            @SuppressLint("RestrictedApi")
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                if(b){
                    av.visibility = View.VISIBLE
                    rootView.fab_search.visibility = View.GONE
                }
            }
            @SuppressLint("RestrictedApi")
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                if(!b){
                    av.visibility = View.GONE
                    rootView.fab_search.visibility = View.VISIBLE
                }
            }
        })
        closeAnim.start()
    }

    override fun onResumeLazy() {
        super.onResumeLazy()
        if(windowSize == 0.0){
            initWindowSize()
        }
    }


}
