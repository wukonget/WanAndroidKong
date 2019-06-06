package com.pengc.wanandroidkong.adapters

import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.bean.NavTypeBean

class NavTypeAdapter : BaseQuickAdapter<NavTypeBean,BaseViewHolder>(R.layout.item_nav_type,ArrayList<NavTypeBean>()) {

    private var currentSelection = 0
    private var oldSelection = 0

    override fun convert(helper: BaseViewHolder?, item: NavTypeBean?) {
        helper?.setText(R.id.typeName,item?.name)
        if(item == data[currentSelection]){
            helper?.getView<LinearLayout>(R.id.back)?.setBackgroundResource(R.color.colorAccent)
            helper?.getView<TextView>(R.id.typeName)?.setTextColor(Color.WHITE)
        }else{
            helper?.getView<LinearLayout>(R.id.back)?.setBackgroundResource(R.color.white)
            helper?.getView<TextView>(R.id.typeName)?.setTextColor(Color.GRAY)
        }
    }

    fun setSelect(position: Int) {
        currentSelection = position
        notifyItemChanged(oldSelection)
        notifyItemChanged(currentSelection)
        oldSelection = currentSelection
    }

    fun getSelectedData(): NavTypeBean {
        return data[currentSelection]
    }
}