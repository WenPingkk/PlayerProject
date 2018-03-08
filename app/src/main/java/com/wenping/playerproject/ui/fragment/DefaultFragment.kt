package com.wenping.playerproject.ui.fragment

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseFragment

/**
 * @author WenPing
 * @date 2018/3/8
 * @decription:视频播放
 *<p>
 */
class DefaultFragment : BaseFragment() {
    override fun initView(): View? {
        val tv = TextView(context)
        tv.gravity = Gravity.CENTER
        tv.setTextColor(Color.RED)
        tv.text = javaClass.simpleName
        return tv
    }
}