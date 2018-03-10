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
        //START_STICKY   粘性的  service强制杀死之后 会尝试重新启动service 不会传递原来的intent(null)
        //START_NOT_STICKY 非粘性的 service强制杀死之后 不会尝试重新启动service
        //START_REDELIVER_INTENT service强制杀死之后 会尝试重新启动service  会传递原来的intent
        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    /**
     * service和binder交互,创建binder接口的实现类;
     */
    inner class AudioBinder : Binder(),Iservice,MediaPlayer.OnPreparedListener {
        /**
         * 实现iService 接口中方法
         */
        override fun updatePlayState() {
            //更新播放的状态
            //获取当前播放状态
            val isPlaying = isPlaying()
            isPlaying?.let {
                if (isPlaying) {
                    //播放-》暂停
                    mediaPlayer?.pause()
                } else {
                    //暂停-》播放
                    mediaPlayer?.start()
                }
            }
        }

         override fun isPlaying(): Boolean? {
            return mediaPlayer?.isPlaying
        }

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