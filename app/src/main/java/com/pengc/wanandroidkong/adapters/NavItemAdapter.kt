package com.pengc.wanandroidkong.adapters

import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseSectionQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.bean.NavSection
import com.pengc.wanandroidkong.bean.NavTypeBean

class NavItemAdapter:BaseSectionQuickAdapter<NavSection,BaseViewHolder>(R.layout.item_nav_type,R.layout.item_nav_header,ArrayList<NavSection>()) {

    private val headerPosition = HashMap<String,Int>()

    override fun convertHead(helper: BaseViewHolder?, item: NavSection?) {
        helper?.setText(R.id.headerName,item?.header)
    }

    override fun convert(helper: BaseViewHolder?, item: NavSection?) {
        helper?.getView<LinearLayout>(R.id.back)?.setBackgroundResource(R.color.white)
        helper?.setText(R.id.typeName,item?.t?.title)
    }

    fun setNewNavData(navData: List<NavTypeBean>?) {
        val sectionData = ArrayList<NavSection>()
        var position = 0
        navData?.forEach { it ->
            sectionData.add(NavSection(true,it.name))
            headerPosition.put(it.name,position)
            position++
            it.articles.forEach {it1->
                sectionData.add(NavSection(it1))
                position++
            }
        }
        setNewData(sectionData)
    }

    fun getHeaderPosition(name:String):Int{
        return if (headerPosition[name] ==null) 0 else (headerPosition[name]!!)
    }
}