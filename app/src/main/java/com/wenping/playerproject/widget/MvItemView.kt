package com.wenping.playerproject.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.wenping.playerproject.R
import com.wenping.playerproject.model.VideosBean

/**
 * @author WenPing
 * @date 2018/3/7
 *<p>
 */
class MvItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init {
        View.inflate(context, R.layout.item_mv, this)
    }

    /**
     * 适配每一个list列表
     */
    fun setData(data: VideosBean) {

    }

}