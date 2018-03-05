package com.wenping.playerproject.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.wenping.playerproject.R

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
class HomeItemView: RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    /**
     * 初始化方法
     */
    init {
        View.inflate(context,R.layout.item_home,this)

    }

}