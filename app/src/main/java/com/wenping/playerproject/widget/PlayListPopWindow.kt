package com.wenping.playerproject.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.PopupWindow
import com.wenping.playerproject.R
import org.jetbrains.anko.find

/**
 * Author WenPing
 * CreateTime 2018/3/10.
 * Description:popUpWindow 显示 播放列表
 * popupwindow不是view，是个窗体
 */
class PlayListPopWindow(context: Context, adapter: BaseAdapter, listener: AdapterView.OnItemClickListener, val window: Window) : PopupWindow() {

    //记录当前窗体的透明度
    var alpha: Float = 0f
    init {
       alpha =  window.attributes.alpha

        //设置布局
        val view = LayoutInflater.from(context).inflate(R.layout.pop_playlist, null, false)
        val listView = view.find<ListView>(R.id.listView)
        //适配
        listView.adapter = adapter
        //设置条目点击事件
        listView.setOnItemClickListener(listener)
        contentView = view
        //设置宽度和高度
        width = ViewGroup.LayoutParams.MATCH_PARENT
        //设置高度为屏幕高度的3/5
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        manager.defaultDisplay.getSize(point)
        val windowH = point.y
        height = (windowH * 3) / 5

        //popupwindow设置焦点
        isFocusable = true
        //设置外部点击的模式
        isOutsideTouchable = true
        //相应返回按钮;(这是支持低版本中返回按键的功能)
        setBackgroundDrawable(ColorDrawable())

        //处理pop和push的动画
        animationStyle = R.style.pop
    }

    //翩若惊鸿,婉若游龙,荣要求去,华茂春松.
    override fun showAsDropDown(anchor: View?, xoff: Int, yoff: Int) {
        super.showAsDropDown(anchor, xoff, yoff)
        //popwindow显示
        val attributes = window.attributes
        attributes.alpha = 0.5f
        //设置到应用程序窗体上面
        window.attributes = attributes
    }

    override fun dismiss() {
        super.dismiss()
        //popwindow已经隐藏了;恢复透明度
        val attributes = window.attributes
        attributes.alpha = alpha
        //设置到应用程序窗体上面
        window.attributes = attributes
    }
}