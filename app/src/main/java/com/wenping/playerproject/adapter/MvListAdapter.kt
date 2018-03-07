package com.wenping.playerproject.adapter

import android.content.Context
import com.wenping.playerproject.base.BaseListAdapter
import com.wenping.playerproject.model.VideosBean
import com.wenping.playerproject.widget.MvItemView

/**
 * @author WenPing
 * @date 2018/3/7
 *<p>
 */
class MvListAdapter : BaseListAdapter<VideosBean, MvItemView>() {

    override fun getItemView(context: Context?): MvItemView {
        return MvItemView(context)
    }

    override fun refreshItemView(itemView: MvItemView, data: VideosBean) {
        itemView.setData(data)
    }

}