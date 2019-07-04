package com.pengc.wanandroidkong.fragment

import android.os.Bundle
import android.view.View
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.activity.LoginActivity
import com.pengc.wanandroidkong.base.BaseLazyFragment
import com.pengc.wanandroidkong.presenter.MePresenter
import com.pengc.wanandroidkong.utils.SpUtil
import kotlinx.android.synthetic.main.fragment_me.*

class MeFragment:BaseLazyFragment<MePresenter>() {

    override fun showLoading(show: Boolean) {

    }

    override fun initData(savedInstanceState: Bundle?) {
        initViews()
    }

    private fun initViews() {
        loginLayout.setOnClickListener {
            activity?.let { it1 -> LoginActivity.launch(it1) }
        }

        logoutBtn.setOnClickListener{
            p.logout()
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_me
    }

    override fun newP(): MePresenter {
        return MePresenter()
    }

    override fun onStartLazy() {
        super.onStartLazy()
        refreshUserStatus()
    }

    private fun refreshUserStatus() {
        var userName = SpUtil.getString(SpUtil.USER_NAME_KEY)?:""
            setUserLogined(userName)
    }

    private fun setUserLogined(userName:String) {
        if(userName.isNotEmpty()){
            loginLayout.visibility = View.GONE
            userInfoLayout.visibility = View.VISIBLE
            userNameView.text = userName
            logoutBtn.visibility = View.VISIBLE
        }else{
            loginLayout.visibility = View.VISIBLE
            userInfoLayout.visibility = View.GONE
            userNameView.text = ""
            logoutBtn.visibility = View.GONE
        }
    }

    fun logoutSucc() {
        setUserLogined("")
        showText("已退出账号")
    }

}