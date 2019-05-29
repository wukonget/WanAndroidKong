package com.pengc.wanandroidkong.activity

import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.adapters.MainFragmentAdapter
import com.pengc.wanandroidkong.base.BaseActivity
import com.pengc.wanandroidkong.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainPresenter>() {

    override fun beforeSetContent() {
        super.beforeSetContent()
        setTheme(R.style.AppTheme)
    }

    override fun initData(savedInstanceState: Bundle?) {
        super.initData(savedInstanceState)
        initViewPager()
        initNav()
    }

    private fun initNav() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> mainViewPager.currentItem = 0
                R.id.nav_todo -> mainViewPager.currentItem = 1
                R.id.nav_me -> mainViewPager.currentItem = 2
                else -> mainViewPager.currentItem = 0
            }
            true
        }
    }

    private fun initViewPager() {
        mainViewPager.adapter = MainFragmentAdapter(supportFragmentManager)
        mainViewPager.offscreenPageLimit = 3
        mainViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                bottom_navigation.menu.getItem(position).isChecked = true
            }

        })
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun newP(): MainPresenter {
        return MainPresenter()
    }
}