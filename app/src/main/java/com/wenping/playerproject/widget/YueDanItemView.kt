package com.wenping.playerproject.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import com.squareup.picasso.Picasso
import com.wenping.playerproject.R
import com.wenping.playerproject.model.YueDanBean
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.item_yuedan.view.*

/**
 * @author WenPing
 * @date 2018/3/6
 * 悦单界面每个条目的自定义view
 *<p>
 */
class YueDanItemView :RelativeLayout{

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    init {
        View.inflate(context, R.layout.item_yuedan,this)
    }

    /**
     * 条目view的初始化
     */
    fun setData(data: YueDanBean.PlayListsBean) {
        //歌单名称
        title_yueDan.text= data.title
        //歌手名称
        author_name.text=data.creator?.nickName
        //歌曲的个数
        count.text=data.videoCount.toString()
       //背景 用Picasso
        Picasso.with(context).load(data.playListBigPic).into(bg_yuedan)

        Picasso.with(context).load(data.playListPic).transform(CropCircleTransformation()).into(author_image)

    }


}