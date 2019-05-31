package com.pengc.wanandroidkong.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pengc.wan_main.app.utils.GlideImageLoader
import com.pengc.wan_main.mvp.ui.adapter.MainListAdapter
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.adapters.DialogTixiAdapter
import com.pengc.wanandroidkong.base.BaseLazyFragment
import com.pengc.wanandroidkong.bean.BannerBean
import com.pengc.wanandroidkong.bean.HotSearchKey
import com.pengc.wanandroidkong.bean.MainListData
import com.pengc.wanandroidkong.bean.TixiBean
import com.pengc.wanandroidkong.presenter.HomePresenter
import com.pengc.wanandroidkong.utils.WebUtil
import kotlinx.android.synthetic.main.dialog_select_tixi.view.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment:BaseLazyFragment<HomePresenter>() {
    private var mDialogR1Adapter: DialogTixiAdapter = DialogTixiAdapter()
    private var mDialogR2Adapter = DialogTixiAdapter()
    private var windowSize: Double = 0.0
    private lateinit var mMainListAdapter: MainListAdapter
    private var loadingCount = 0

    private fun loadData() {
        p.loadBanner()
        p.loadListData(0,true)
        p.loadSearchKeys()
        p.loadTixi()
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
        mMainListAdapter.setOnItemClickListener { adapter, _, position ->
            val data = (adapter as MainListAdapter).data[position]
            WebUtil.loadUrl(
            activity as AppCompatActivity,
                data.link,
                data.title
        ) }
        rootView.recyclerView.adapter = mMainListAdapter
        rootView.recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        initBanner()
        initRecycler()
        initFab()
        initOther()
        loadData()
    }

    private fun initOther() {
        rootView.tixi.setOnClickListener {
            showSelectTixiDialog()
        }
    }

    /**
     * 显示体系选择弹框
     */
    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    @SuppressLint("InflateParams", "SetTextI18n")
    private fun showSelectTixiDialog() {

        val dialogView = layoutInflater.inflate(R.layout.dialog_select_tixi,null,false)
        val r1 = dialogView.rv_dialog_1
        val r2 = dialogView.rv_dialog_2
        r1.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        r2.layoutManager = LinearLayoutManager(activity,RecyclerView.VERTICAL,false)
        r1.adapter = mDialogR1Adapter
        r2.adapter = mDialogR2Adapter
        mDialogR1Adapter.setOnItemClickListener { adapter, _, position ->
            mDialogR2Adapter.setNewData((adapter.data[position] as TixiBean).children)
            mDialogR1Adapter.setSelect(position)
            mDialogR2Adapter.setSelect(0)
        }

        mDialogR2Adapter.setOnItemClickListener { _, _, position ->
            mDialogR2Adapter.setSelect(position)
        }

        val selectTixiDialog = AlertDialog.Builder(activity as Context)
            .setView(dialogView)
            .setPositiveButton("确定") { _, _ ->
                    tixi.text = "${mDialogR1Adapter.getSelectedData().name}/${mDialogR2Adapter.getSelectedData().name}"
                    p.loadListData(0,true,mDialogR2Adapter.getSelectedData().id)
            }.setNegativeButton("取消",null)
            .create()
        selectTixiDialog.window.setWindowAnimations(R.style.DialogAnimations)
        if(!selectTixiDialog.isShowing){
            selectTixiDialog.show()
        }
    }

    private fun initFab() {
        rootView.fab_search.setOnClickListener {
            showSearchPop(true)
        }
        rootView.back_search.setOnClickListener {
            showSearchPop(false)
        }

        rootView.av.setOnClickListener {  }
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
            val bannerBean = bannerData?.get(position)
            if(bannerBean!=null) {
                WebUtil.loadUrl(activity as AppCompatActivity, bannerBean.url, bannerBean.title)
            }
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

    @Suppress("DEPRECATION")
    private fun initWindowSize() {
        val width = if(activity == null) 0 else activity!!.windowManager.defaultDisplay.width
        val height = if(activity == null) 0 else activity!!.windowManager.defaultDisplay.height
        windowSize = Math.sqrt((width*width+height*height).toDouble())
    }

    private fun showSearchPop(b: Boolean) {
        val startR: Float
        val endR: Float
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

    override fun onActivityBackPressed(): Boolean {
        return if(rootView.av.visibility == View.VISIBLE) {
            showSearchPop(false)
            true
        }else{
            false
        }
    }

    fun setSearchKeys(searchKeysData: List<HotSearchKey>?) {
        val tags = ArrayList<String>()
        searchKeysData?.forEach { tags.add(it.name) }
        searchTagGroup.setTags(tags)
        searchTagGroup.setOnTagClickListener {

        }
    }

    fun setTixiData(tixiData: List<TixiBean>?) {
        mDialogR1Adapter.setNewData(tixiData)
        mDialogR2Adapter.setNewData((tixiData?.get(0) as TixiBean).children)
    }
}
