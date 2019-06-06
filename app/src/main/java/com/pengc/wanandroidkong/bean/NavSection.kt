package com.pengc.wanandroidkong.bean

import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.entity.SectionEntity

class NavSection:SectionEntity<NavItemBean> {
    constructor(isHeader:Boolean,header:String):super(isHeader,header)
    constructor(t:NavItemBean):super(t)
}