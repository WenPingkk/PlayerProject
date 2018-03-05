package com.wenping.playerproject.ui.fragment

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.wenping.playerproject.base.BaseFragment

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
class MvFragment : BaseFragment() {

    override fun initView(): View? {
        val tv = TextView(activity)
        tv.gravity = Gravity.CENTER
        tv.setTextColor(Color.RED)
        tv.setText(javaClass.simpleName)
        return tv
    }
}