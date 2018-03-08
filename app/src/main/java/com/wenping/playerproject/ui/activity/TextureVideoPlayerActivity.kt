package com.wenping.playerproject.ui.activity

import android.graphics.SurfaceTexture
import android.media.MediaPlayer
import android.util.Log
import android.view.Surface
import android.view.TextureView
import android.widget.VideoView
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.model.VideoPlayBean
import kotlinx.android.synthetic.main.activity_texture_videopalyer.*
import kotlinx.android.synthetic.main.activity_videopalyer.*

/**
 * Author WenPing
 * CreateTime 2018/3/7.
 *
 */
class TextureVideoPlayerActivity : BaseActivity(), TextureView.SurfaceTextureListener {
    override fun onSurfaceTextureSizeChanged(p0: SurfaceTexture?, p1: Int, p2: Int) {
        //视图大小发生改变
    }

    override fun onSurfaceTextureUpdated(p0: SurfaceTexture?) {
        //视图更新
    }

    override fun onSurfaceTextureDestroyed(p0: SurfaceTexture?): Boolean {
        mediaPlayer?.let {
            mediaPlayer.stop()
            mediaPlayer.release()
        }
        //视图销毁
        return true
    }

    override fun onSurfaceTextureAvailable(p0: SurfaceTexture?, p1: Int, p2: Int) {
        //视图可获取
        videoPlayBean?.let {
            mediaPlayer.setDataSource(it.url)

            mediaPlayer.setSurface(Surface(p0))
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener {
                it.start()
                textureView.rotation = 90f
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_texture_videopalyer
    }

    var videoPlayBean: VideoPlayBean? = null
    val mediaPlayer by lazy { MediaPlayer() }

    override fun initData() {
        videoPlayBean = intent.getParcelableExtra<VideoPlayBean>("item")
        textureView.surfaceTextureListener = this
    }
}