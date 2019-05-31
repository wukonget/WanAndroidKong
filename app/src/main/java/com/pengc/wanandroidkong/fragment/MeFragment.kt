package com.pengc.wanandroidkong.fragment

import android.os.Bundle
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.base.BaseLazyFragment
import com.pengc.wanandroidkong.presenter.MePresenter

class MeFragment:BaseLazyFragment<MePresenter>() {

    override fun showLoading(show: Boolean) {

    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun newP(): MePresenter {
        return MePresenter()
    }
}