package com.pengc.wanandroidkong.base

import cn.droidlover.xdroidmvp.mvp.IPresent
import cn.droidlover.xdroidmvp.mvp.XFragment

abstract class BaseFragment<P :IPresent<*>>: XFragment<P>() {
}