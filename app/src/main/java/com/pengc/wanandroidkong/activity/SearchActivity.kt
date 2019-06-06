package com.pengc.wanandroidkong.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.base.BaseActivity
import com.pengc.wanandroidkong.presenter.SearchPresenter

class SearchActivity:BaseActivity<SearchPresenter>() {

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

    }
}