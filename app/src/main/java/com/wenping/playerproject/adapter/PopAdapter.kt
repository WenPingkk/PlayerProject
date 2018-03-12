package com.wenping.playerproject.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.wenping.playerproject.model.AudioBean
import com.wenping.playerproject.widget.PopListItemView

/**
 * @author WenPing
 * @date 2018/3/12
 * @decription:播放列表popwindow的适配器
 *<p>
 */
class PopAdapter(var list: List<AudioBean>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView: PopListItemView? = null
        //convertView不能被赋值
        if (convertView == null) {
            itemView = PopListItemView(parent?.context)
        } else {
            itemView = convertView as PopListItemView
        }
        itemView.setData(list.get(position))
        return itemView
    }

    override fun getItem(position: Int): AudioBean {
        return list.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return list.size
    }
}