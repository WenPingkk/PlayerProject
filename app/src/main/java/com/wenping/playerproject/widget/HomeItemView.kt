package com.wenping.playerproject.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import com.wenping.playerproject.R
import com.wenping.playerproject.model.HomeItemBean
import kotlinx.android.synthetic.main.activity_setting.view.*
import kotlinx.android.synthetic.main.item_home.view.*

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

    /**
     * 刷新条目和view的操作
     */
    fun setData(data: HomeItemBean) {
        //歌曲名称
        title.setText(data.title)
        //description
        description.setText(data.description)

        Picasso.with(context).load(data.posterPic).into(bg)
    }
    

}