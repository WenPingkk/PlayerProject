package com.wenping.playerproject.service

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.wenping.playerproject.model.AudioBean

/**
 * @author WenPing
 * @date 2018/3/9
 * @decription: 播放音乐的服务
 *<p>
 */
class AudioService : Service() {

    var list:ArrayList<AudioBean>? = null;
    var position:Int =0
    var mediaPlayer:MediaPlayer? = null
    val binder:AudioBinder by lazy { AudioBinder() }
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //在这里获取集合以及position
        list = intent?.getParcelableArrayListExtra<AudioBean>("list")
        position = intent?.getIntExtra("position",-1)?:-1
        //开始播放音乐
        binder.playItem()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    /**
     * service和binder交互,创建binder接口的实现类;
     */
    inner class AudioBinder : Binder(),Iservice,MediaPlayer.OnPreparedListener {
        override fun onPrepared(mp: MediaPlayer?) {
            mediaPlayer?.start()
        }

        fun playItem() {
            mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                it.setDataSource(list?.get(position)?.data)
                it.prepareAsync()
                it.setOnPreparedListener(this)
            }

        }
    }
}