package com.wenping.playerproject.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import com.wenping.playerproject.model.AudioBean
import de.greenrobot.event.EventBus
import java.util.*

/**
 * @author WenPing
 * @date 2018/3/9
 * @decription: 播放音乐的服务
 *<p>
 */
class AudioService : Service() {

    var list: ArrayList<AudioBean>? = null;
    var position: Int = 0
    var mediaPlayer: MediaPlayer? = null
    val binder: AudioBinder by lazy { AudioBinder() }

    companion object {
        val MODE_ALL = 1
        val MODE_SINGLE = 2
        val MODE_RANDOM = 3
    }

    var mode = MODE_ALL

    val sp by lazy {
        getSharedPreferences("config", Context.MODE_PRIVATE)
    }

    override fun onCreate() {
        super.onCreate()
        mode = sp.getInt("mode", 1)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //在这里获取集合以及position
        list = intent?.getParcelableArrayListExtra<AudioBean>("list")
        position = intent?.getIntExtra("position", -1) ?: -1
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
    inner class AudioBinder : Binder(), Iservice, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener {
        /**
         *播放上一首
         */
        override fun playPre() {
            //获取要播放的歌曲的position
            list?.let {
                when (mode) {
                    MODE_RANDOM -> list?.let { position = Random().nextInt(it.size - 1) }
                    else -> {
                        if (position == 0) {
                            position == it.size - 1

                        } else {
                            position--
                        }
                    }
                }
            }
            playItem()
        }

        /**
         * 播放下一首
         */
        override fun playNext() {
            list?.let {
                when (mode) {
                    MODE_RANDOM -> position = Random().nextInt(it.size - 1)
                    else -> {
                        position = (position + 1) % it.size
                    }
                }
            }
            playItem()
        }

        /**
         * 获取播放模式
         */
        override fun getPlayMode(): Int {
            return mode
        }

        /**
         * 修改播放模式
         * MODEA_ALL;MODE_SINGLE;MODE_RANDOM
         */
        override fun updatePlayMode() {
            when (mode) {
                MODE_ALL -> mode = MODE_SINGLE
                MODE_SINGLE -> mode = MODE_RANDOM
                MODE_RANDOM -> mode = MODE_ALL
            }
            //保存播放模式
            sp.edit().putInt("mode", mode).apply()
        }

        /**
         * 播放完成后的回调-》自动播放下一首
         */
        override fun onCompletion(mp: MediaPlayer?) {

            autoPlayNext()
        }

        //根据播放模式播放下一首
        private fun autoPlayNext() {
            when (mode) {
                MODE_ALL -> {
//                            if (position == list.size - 1) {
//                                position = 0
//                            } else {
//                                position++
//                            }
                    list?.let {
                        position = (position + 1) % it.size
                    }
                }
            //MODE_SINGLE-> //不需要变化
                MODE_RANDOM -> list?.let {
                    position = Random().nextInt(it.size)
                }
            }
            playItem()
        }


        //跳转到当前进度进行播放
        override fun seekTo(progress: Int) {
            mediaPlayer?.seekTo(progress)
        }

        override fun getProgress(): Int {
            return mediaPlayer?.currentPosition ?: 0
        }

        override fun getDuration(): Int {
            //获取总进度
            return mediaPlayer?.duration ?: 0
        }


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
                    EventBus.getDefault().post(list?.get(position))
                } else {
                    //暂停-》播放
                    mediaPlayer?.start()
                    EventBus.getDefault().post(list?.get(position))
                }
            }
        }

        override fun isPlaying(): Boolean? {
            return mediaPlayer?.isPlaying
        }

        override fun onPrepared(mp: MediaPlayer?) {
            //播放音乐
            mediaPlayer?.start()
            //通知界面更新
            notifyUpdateUI()
        }

        /**
         * 通知页面更新
         */
        private fun notifyUpdateUI() {
            //广播的形式；eventbus：相当于应用内的广播;参数匹配！
            EventBus.getDefault().post(list?.get(position))
        }

        fun playItem() {

            //如果mediaplayer存在就先释放掉
            if (mediaPlayer != null) {
                mediaPlayer?.let {
                    it.reset()
                    it.release()
                }
            }

            mediaPlayer = MediaPlayer()
            mediaPlayer?.let {
                it.setDataSource(list?.get(position)?.data)
                it.prepareAsync()
                it.setOnPreparedListener(this)
                it.setOnCompletionListener(this)
            }
        }
    }
}