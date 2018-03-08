package com.wenping.playerproject.ui.activity

import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.model.VideoPlayBean
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard
import kotlinx.android.synthetic.main.activity_jiecaovideoplayer.*
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.releaseAllVideos
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.backPress
import io.vov.vitamio.utils.Log


/**
 * @author WenPing
 * @date 2018/3/8
 *<p>
 */
class JiecaoPlayerActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_jiecaovideoplayer
    }

    override fun initData() {

        val data = intent.data
        println("data$data")

        if (data == null) {
            //应用内
            val videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
            jiecaovideoplayer.setUp(videoPlayBean.url,
                    JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, videoPlayBean.title)
        }else {
            if (data.toString().startsWith("http")) {
                //应用外网络视频请求
                //应用外响应
                jiecaovideoplayer.setUp(data.toString(),
                        JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, data.toString())
            } else {
                //应用外的本地视频请求
                //应用外响应
                jiecaovideoplayer.setUp(data.path,
                        JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, data.toString())
            }

        }


//        jzVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640")
    }

    override fun onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        JCVideoPlayer.releaseAllVideos()
    }

    override fun onDestroy() {
        super.onDestroy()
        JCVideoPlayer.releaseAllVideos()
    }

}