package com.pengc.wan_main.mvp.ui.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.pengc.wanandroidkong.R
import com.pengc.wanandroidkong.bean.MainListItemData
import me.gujun.android.taggroup.TagGroup

class MainListAdapter(layoutResId:Int = R.layout.item_mainlist) : BaseQuickAdapter<MainListItemData,BaseViewHolder>(layoutResId,ArrayList()) {
    override fun convert(helper: BaseViewHolder?, item: MainListItemData?) {
        helper?.setText(R.id.title,item?.title)
        helper?.setText(R.id.origin,"来源:${item?.superChapterName}-${item?.chapterName}")
        helper?.setText(R.id.authorName,"作者:${item?.author}")
        helper?.setText(R.id.zan,"赞:${item?.zan}")
        helper?.setText(R.id.date,item?.niceDate)

        val tagGroup = helper?.getView<TagGroup>(R.id.tagGroup)
        val tagStrings = ArrayList<String>()
        item?.tags?.forEach {
            tagStrings.add(it.name)
        }
        tagGroup?.setTags(tagStrings)
        tagGroup?.visibility = if(tagStrings.size>0) View.VISIBLE else View.GONE
    }
}