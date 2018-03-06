package com.wenping.playerproject.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wenping.playerproject.base.BaseListAdapter
import com.wenping.playerproject.model.HomeItemBean
import com.wenping.playerproject.widget.HomeItemView
import com.wenping.playerproject.widget.LoadMoreView

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
class HomeAdapter : BaseListAdapter<HomeItemBean, HomeItemView>() {
    override fun getItemView(context: Context?): HomeItemView {
        return HomeItemView(context)
    }

    override fun refreshItemView(itemView: HomeItemView, data: HomeItemBean) {
        itemView.setData(data)
    }

}
