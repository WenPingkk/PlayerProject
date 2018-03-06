package com.wenping.playerproject.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wenping.playerproject.base.BaseListAdapter
import com.wenping.playerproject.model.YueDanBean
import com.wenping.playerproject.widget.LoadMoreView
import com.wenping.playerproject.widget.YueDanItemView

/**
 * @author WenPing
 * @date 2018/3/6
 * 悦单界面的adapter
 *<p>
 */
class YueDanAdapter : BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView>() {
    override fun getItemView(context: Context?): YueDanItemView {
        return YueDanItemView(context)
    }

    override fun refreshItemView(itemView: YueDanItemView, data: YueDanBean.PlayListsBean) {
        itemView.setData(data)
    }

}