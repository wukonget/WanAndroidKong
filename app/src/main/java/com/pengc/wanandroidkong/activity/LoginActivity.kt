package com.pengc.wanandroidkong.activity

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.base.BaseActivity
import com.pengc.wanandroidkong.presenter.LoginPreseneter
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.random.Random
import kotlin.random.asJavaRandom

class LoginActivity : BaseActivity<LoginPreseneter>() {

    private var isLogin = true
    private lateinit var identifyText: String

    companion object {

        fun launch(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun newP(): LoginPreseneter {
        return LoginPreseneter()
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initViews()
    }

    private fun initViews() {

        doLogin.setOnClickListener {
            val username = username.text.toString()
            val password = password.text.toString()
            val repassword = repassword.text.toString()
            val inputIdentify = input_identify.text.toString()
            if (checkInput(username, password, repassword, inputIdentify, isLogin)) {
                if (isLogin) {
                    p.login(username, password)
                } else {
                    p.register(username, password, repassword)
                }
            }
        }

        register.setOnClickListener {
            showRegisterViews(isLogin)
        }

        gene_identify.setOnClickListener {
            refreshRandom()
        }

    }

    /**
     * 显示或隐藏注册所需控件
     */
    private fun showRegisterViews(show: Boolean) {
        if (show) {
            repasswordLayout.visibility = View.VISIBLE
            identifyLayout.visibility = View.VISIBLE
            register.text = "去登陆"
            refreshRandom()
        } else {
            repasswordLayout.visibility = View.GONE
            identifyLayout.visibility = View.GONE
            register.text = "没有账号？注册一个"
        }
        isLogin = !isLogin

    }

    private fun refreshRandom() {
        identifyText = (10000..99999).random().toString()
        gene_identify.text = identifyText
    }

    private fun checkInput(
        username: String,
        passWord: String,
        rePassWord: String,
        inputIdentify: String,
        isLogin: Boolean
    ): Boolean {
        if (username.isEmpty()) {
            showMessage("验证失败：用户名不能为空")
            return false
        }
        if (passWord.isEmpty()) {
            showMessage("验证失败：密码不能为空")
            return false
        }
        if (!isLogin && passWord != rePassWord) {
            showMessage("验证失败：两次密码输入不相同，重新输入")
            return false
        }
        if (!isLogin) {
            if (inputIdentify.isEmpty()) {
                showMessage("验证失败：验证码不能为空")
                return false
            } else {
                if (inputIdentify != identifyText) {
                    showMessage("验证失败：验证码不正确")
                    refreshRandom()
                    return false
                }
            }
        }
        return true
    }


    @SuppressLint("RestrictedApi")
    override fun showLoading(show: Boolean) {
        doLogin.visibility = if (show) View.GONE else View.VISIBLE
        loading.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun loginSucc() {
//        showMessage("登陆成功")
//        toMain()
        finish()
    }

    fun loginFail(error: String?) {
        showMessage("登陆失败:$error")
    }

//    private fun toMain() {
//        val intent = Intent(this, MainActivity::class.java)
//        startActivity(
//            intent,
//            ActivityOptions.makeSceneTransitionAnimation(
//                this@LoginActivity,
//                doLogin,
//                getString(R.string.loginTrans)
//            ).toBundle()
//        )
//        finish()
//    }

    fun registerSucc() {
        showRegisterViews(false)
        showMessage("注册成功，使用新注册账号登陆")
    }

    fun registerFail(errorMsg: String?) {
        showMessage("注册失败:$errorMsg")
    }
}