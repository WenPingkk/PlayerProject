package com.wenping.playerproject.widget

import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import com.wenping.playerproject.R

/**
 * Author WenPing
 * CreateTime 2018/3/10.
 * Description:popUpWindow 显示 播放列表
 * popupwindow不是view，是个窗体
 */
class PlayListPopWindow (context:Context): PopupWindow() {



    init {
        //设置布局
        val view = LayoutInflater.from(context).inflate(R.layout.pop_playlist, null, false)

        contentView = view
        //设置宽度和高度
        width = ViewGroup.LayoutParams.MATCH_PARENT
        //设置高度为屏幕高度的3/5
        val manager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val point = Point()
        manager.defaultDisplay.getSize(point)
        val windowH = point.y
        height = (windowH*3)/5

        //popupwindow设置焦点
        isFocusable = true
        //设置外部点击的模式
        isOutsideTouchable = true
        //相应返回按钮;(这是支持低版本中返回按键的功能)
        setBackgroundDrawable(ColorDrawable())
    }

}