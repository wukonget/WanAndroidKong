package com.pengc.wanandroidkong.fragment

import android.os.Bundle
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.base.BaseLazyFragment
import com.pengc.wanandroidkong.presenter.TodoPresenter

class TodoFragment:BaseLazyFragment<TodoPresenter>() {

    override fun showLoading(show: Boolean) {

    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_todo
    }

    override fun newP(): TodoPresenter {
        return TodoPresenter()
    }
}