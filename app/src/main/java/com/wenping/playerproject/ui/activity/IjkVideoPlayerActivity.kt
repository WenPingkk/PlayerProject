package com.wenping.playerproject.ui.activity

import android.util.Log
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_ijkvideopalyer.*

/**
 * @author WenPing
 * @date 2018/3/8
 *<p>
 */
class IjkVideoPlayerActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_ijkvideopalyer
    }

    override fun initData() {
        val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
        Log.e("wen",videoPlayBean.toString())
        ijkVideoView.setVideoPath(videoPlayBean.url)
        ijkVideoView.setOnPreparedListener {
            ijkVideoView.start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //停止播放
        ijkVideoView.stopPlayback()
    }
}