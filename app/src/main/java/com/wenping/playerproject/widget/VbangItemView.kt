package com.wenping.playerproject.widget

import android.content.Context
import android.text.format.Formatter
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.wenping.playerproject.R
import com.wenping.playerproject.model.AudioBean
import kotlinx.android.synthetic.main.item_vbang.view.*

/**
 * @author WenPing
 * @date 2018/3/8
 * @decription:
 *<p>
 */
class VbangItemView : RelativeLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init {
        View.inflate(context,R.layout.item_vbang,this)
    }

    /**
     * view+data
     */
    fun setData(itemBean: AudioBean) {
        //歌曲名称
        title.text = itemBean.displayName
        //歌手名
        artist.text = itemBean.artist
        //歌曲大小
        size.text = Formatter.formatFileSize(context,itemBean.size)
    }
}