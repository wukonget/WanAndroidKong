package com.pengc.wanandroidkong.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.activity.WebViewActivity
import com.pengc.wanandroidkong.adapters.NavItemAdapter
import com.pengc.wanandroidkong.adapters.NavTypeAdapter
import com.pengc.wanandroidkong.base.BaseLazyFragment
import com.pengc.wanandroidkong.bean.NavSection
import com.pengc.wanandroidkong.bean.NavTypeBean
import com.pengc.wanandroidkong.presenter.NavPresenter
import com.pengc.wanandroidkong.utils.TopSmoothScroller
import kotlinx.android.synthetic.main.fragment_nav.*
import kotlinx.android.synthetic.main.fragment_nav.view.*

class NavFragment : BaseLazyFragment<NavPresenter>() {

    private val typeAdapter: NavTypeAdapter = NavTypeAdapter()
    private val navItemAdapter: NavItemAdapter = NavItemAdapter()
    private val rHeaderManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    private val rContentManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)

    private lateinit var topSmoothScroller :TopSmoothScroller

    override fun showLoading(show: Boolean) {
        loadingView.visibility = if(show) View.VISIBLE else View.GONE
    }

    override fun initData(savedInstanceState: Bundle?) {
        initTypeRecyclerView()
        initContentRecyclerView()
        p.loadData()
    }

    private fun initContentRecyclerView() {
        topSmoothScroller = TopSmoothScroller(activity as Context)
        rootView.recycler_type.layoutManager = rHeaderManager
        rootView.recycler_type.adapter = typeAdapter
        typeAdapter.setOnItemClickListener { adapter, view, position ->
            typeAdapter.setSelect(position)
            val p = navItemAdapter.getHeaderPosition((adapter.data[position] as NavTypeBean).name)
//            rContentManager.scrollToPositionWithOffset(p,0)

            topSmoothScroller.targetPosition = p
            rContentManager.startSmoothScroll(topSmoothScroller)
        }
    }

    private fun initTypeRecyclerView() {
        rootView.recycler_content.layoutManager = rContentManager
        rootView.recycler_content.adapter = navItemAdapter
        navItemAdapter.setOnItemClickListener { adapter, view, position ->
            val itemBean:NavSection = adapter.data[position] as NavSection
            if(!itemBean.isHeader){
            activity?.let { WebViewActivity.launch(it,itemBean.t.link,itemBean.t.title) }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_nav
    }

    override fun newP(): NavPresenter {
        return NavPresenter()
    }

    fun setNavData(navData: List<NavTypeBean>?) {
        typeAdapter.setNewData(navData)
        navItemAdapter.setNewNavData(navData)
    }
}