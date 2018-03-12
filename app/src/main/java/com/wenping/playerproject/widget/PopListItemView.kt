package com.wenping.playerproject.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.wenping.playerproject.R
import com.wenping.playerproject.model.AudioBean
import kotlinx.android.synthetic.main.item_pop.view.*

/**
 * @author WenPing
 * @date 2018/3/12
 * @decription:
 *<p>
 */
class PopListItemView : RelativeLayout {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init {
        View.inflate(context, R.layout.item_pop,this)
    }

    fun setData(data: AudioBean) {
        //title
        title.text = data.displayName
        //artist
        artist.text = data.artist
    }
}