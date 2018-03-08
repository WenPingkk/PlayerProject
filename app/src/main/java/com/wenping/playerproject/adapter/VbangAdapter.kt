package com.wenping.playerproject.adapter

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter

/**
 * @author WenPing
 * @date 2018/3/8
 * @decription:
 *<p>
 */
class VbangAdapter(context:Context?,cursor:Cursor?,isRequest:Boolean) : CursorAdapter(context,cursor,isRequest) {
    /**
     * newView 创建条目view
     */
    override fun newView(p0: Context?, p1: Cursor?, p2: ViewGroup?): View {

    }

    /**
     * 进行view和data的绑定
     */
    override fun bindView(p0: View?, p1: Context?, p2: Cursor?) {

    }

}