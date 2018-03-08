package com.wenping.playerproject.ui.activity

import android.util.Log
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.model.VideoPlayBean
import io.vov.vitamio.LibsChecker
import kotlinx.android.synthetic.main.activity_vitamiovideo.*

/**
 * @author WenPing
 * @date 2018/3/8
 *<p>
 */
class VitamioVideoActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_vitamiovideo
    }

    override fun initData() {
        //初始化vitamio

        LibsChecker.checkVitamioLibs(this)

        val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
        Log.e("wen",videoPlayBean.toString())
        vitamioVideoView.setVideoPath(videoPlayBean.url)
        vitamioVideoView.setOnPreparedListener {
            vitamioVideoView.start()
        }
    }

}