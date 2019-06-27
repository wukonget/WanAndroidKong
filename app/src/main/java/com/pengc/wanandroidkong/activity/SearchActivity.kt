package com.pengc.wanandroidkong.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pengc.wan_main.mvp.ui.adapter.MainListAdapter
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.base.BaseActivity
import com.pengc.wanandroidkong.bean.MainListData
import com.pengc.wanandroidkong.presenter.SearchPresenter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity:BaseActivity<SearchPresenter>() {

    private var loadingCount: Int = 0
    private var currentQueryText: String = ""
    private lateinit var mSearchListAdapter: MainListAdapter

    companion object{
        private const val SEARCH_KEY_TAG = "search_key_tag"
        fun launch(context: Context, searchKey:String){
            val intent = Intent(context, SearchActivity::class.java)
            intent.putExtra(SEARCH_KEY_TAG,searchKey)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun newP(): SearchPresenter {
        return SearchPresenter()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        initRecycler()
        initSearch()
    }

    private fun initSearch() {
        val searchKey = intent.getStringExtra(SEARCH_KEY_TAG)

        val searchViewTextView = searchView.findViewById<EditText>(R.id.search_src_text)
        searchViewTextView.textSize = 12f
        searchViewTextView.setTextColor(resources.getColor(R.color.colorAccent))

        val searchViewIcon = searchView.findViewById<ImageView>(R.id.search_button)
        searchViewIcon.visibility = View.GONE

        searchView.isSubmitButtonEnabled = true
        searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchView.setQuery(searchKey, true)
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                currentQueryText = query?:""
                p.loadSearchData(0,true,currentQueryText)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText==null || newText!!.isEmpty()){
//                    p.loadSearchData(0,true,"")
                    mSearchListAdapter?.setNewData(null)
                    return true
                }
                return false
            }

        })

        p.loadSearchData(0,true,searchKey)
        currentQueryText = searchKey
    }

    private fun initRecycler() {
        swipeRefresh.setColorSchemeResources(R.color.colorAccent)
        swipeRefresh.setOnRefreshListener {
            p.loadSearchData(0, true, currentQueryText)
        }
        mSearchListAdapter = MainListAdapter()
        mSearchListAdapter.setOnLoadMoreListener({
            p.loadMore(object : SearchPresenter.SearchLoadMoreListener {
                override fun loadMoreEnd() {
                    mSearchListAdapter.loadMoreEnd()
                }

                override fun loadMoreSuc() {
                    mSearchListAdapter.loadMoreComplete()
                }

                override fun loadMoreFail() {
                    mSearchListAdapter.loadMoreFail()
                }

            })
        }, recyclerView)
        mSearchListAdapter.setOnItemClickListener { adapter, _, position ->
            val data = (adapter as MainListAdapter).data[position]
            WebViewActivity.launch(
                this,
                data.link,
                data.title
            ) }
        recyclerView.adapter = mSearchListAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    fun showLoading(show: Boolean) {
        if(show) loadingCount++ else loadingCount--
        swipeRefresh?.isRefreshing = loadingCount>0
    }

    fun setSearchListData(data: MainListData?, pullToRefresh: Boolean) {
        if(pullToRefresh) {
            mSearchListAdapter.setNewData(data?.datas)
        }else{
            data?.datas?.let { mSearchListAdapter.addData(it) }
        }
    }

    override fun onBackPressed() {
        searchView.clearFocus()
        super.onBackPressed()
    }
}