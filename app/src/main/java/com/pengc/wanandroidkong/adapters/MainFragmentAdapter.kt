package com.pengc.wanandroidkong.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.pengc.wanandroidkong.fragment.HomeFragment
import com.pengc.wanandroidkong.fragment.MeFragment
import com.pengc.wanandroidkong.fragment.NavFragment
import com.pengc.wanandroidkong.fragment.TodoFragment

class MainFragmentAdapter(fragmentManager: FragmentManager):FragmentPagerAdapter(fragmentManager) {

    val fragments = arrayOf(HomeFragment(),NavFragment(),TodoFragment(),MeFragment())

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return 4
    }


}