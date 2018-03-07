package com.wenping.playerproject.adapter

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.wenping.playerproject.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author WenPing
 * @date 2018/3/7
 *<p>
 */
class MvPagerFragment : BaseFragment() {
    var name:String?  =null
    override fun init() {
        //获取传递的数据
        name = arguments?.getString("args")
    }

    override fun initView(): View? {
        val tv = TextView(context)
        tv.gravity = Gravity.CENTER
        tv.setTextColor(Color.RED)
        tv.text = javaClass.simpleName+name
        return tv

    }
}