package com.wenping.playerproject.adapter

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import com.wenping.playerproject.model.AudioBean
import com.wenping.playerproject.widget.VbangItemView

/**
 * @author WenPing
 * @date 2018/3/8
 * @decription: 音乐列表
 *<p>
 */
class VbangAdapter(context:Context?,cursor:Cursor?,isRequest:Boolean) : CursorAdapter(context,cursor,isRequest) {
    /**
     * newView 创建条目view
     */
    override fun newView(p0: Context?, p1: Cursor?, p2: ViewGroup?): View {
        return VbangItemView(p0)
    }

    /**
     * 进行view和data的绑定
     */
    override fun bindView(p0: View?, p1: Context?, p2: Cursor?) {
        //view
        val itemView = p0 as VbangItemView
        //对cursor进行处理,
        val itemBean = AudioBean.getAudioBean(p2)
        //view和数据的绑定
        itemView.setData(itemBean)
    }

}