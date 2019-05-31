package com.pengc.wanandroidkong.adapters

import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.bean.TixiBean

class DialogTixiAdapter :BaseQuickAdapter<TixiBean,BaseViewHolder>(R.layout.item_sim,ArrayList()) {
    private var currentSelection = 0
    private var oldSelection = 0
    override fun convert(helper: BaseViewHolder?, item: TixiBean?) {
        helper?.setText(R.id.text,item?.name)
        if(item == data[currentSelection]){
            helper?.getView<LinearLayout>(R.id.back)?.setBackgroundResource(R.color.colorAccent)
            helper?.getView<TextView>(R.id.text)?.setTextColor(Color.WHITE)
        }else{
            helper?.getView<LinearLayout>(R.id.back)?.setBackgroundResource(R.color.white)
            helper?.getView<TextView>(R.id.text)?.setTextColor(Color.GRAY)
        }
    }

    fun setSelect(position: Int) {
        currentSelection = position
        notifyItemChanged(oldSelection)
        notifyItemChanged(currentSelection)
        oldSelection = currentSelection
    }

    fun getSelectedData():TixiBean{
        return data[currentSelection]
    }
}