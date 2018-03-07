package com.wenping.playerproject.ui.activity

import android.util.Log
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_videopalyer.*

/**
 * Author WenPing
 * CreateTime 2018/3/7.
 *
 */
class VideoPlayerActivity: BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_videopalyer
    }

    override fun initData() {
        val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
        Log.e("wen",videoPlayBean.toString())
        videoView.setVideoPath(videoPlayBean.url)
        videoView.start()
    }
}