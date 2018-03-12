package com.wenping.playerproject.service

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import android.view.WindowManager
import android.widget.RemoteViews
import com.wenping.playerproject.R
import com.wenping.playerproject.model.AudioBean
import com.wenping.playerproject.ui.activity.AudioPlayerActivity
import com.wenping.playerproject.ui.activity.MainActivity
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
    var position: Int = -5//当前正在播放的position
    var mediaPlayer: MediaPlayer? = null
    val binder: AudioBinder by lazy { AudioBinder() }

    var manager:NotificationManager? = null
    var notification:Notification? = null

    val FROM_PRE= 1
    val FROM_NEXT= 2
    val FROM_STATE= 3
    val FROM_CONTENT= 4

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
        //判断进入service方法
        val from = intent?.getIntExtra("from", -1)
        when (from) {
            FROM_PRE->{binder.playPre()}
            FROM_NEXT->{binder.playNext()}
            FROM_CONTENT->{binder.notifyUpdateUI()}
            FROM_STATE->{binder.updatePlayState()}
            else->{
                //在这里获取集合以及position
                val pos = intent?.getIntExtra("position", -1) ?: -1
                if (position != pos) {
                    position = pos
                    //想要播放的条目和正在播放的条目不是同一首歌曲
                    list = intent?.getParcelableArrayListExtra<AudioBean>("list")
                    //开始播放音乐
                    binder.playItem()
                } else {
                    //主动通知界面更新
                    binder.notifyUpdateUI()
                }
            }
        }
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
         * 播放当前位置的歌曲
         */
        override fun playPosition(position: Int) {
            //指定this具体的指向 用@xxx
           this@AudioService.position = position
            playItem()
        }

        //获取数据集合
        override fun getPlayList(): List<AudioBean>? {
            return list
        }

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
                    pause()
                } else {
                    start()
                }
            }
        }

        private fun start() {
            //暂停-》播放
            mediaPlayer?.start()
            EventBus.getDefault().post(list?.get(position))
            //更新图标
            notification?.contentView?.setImageViewResource(R.id.state,R.mipmap.btn_audio_play_normal)
            //重新显示
            manager?.notify(1,notification)
        }

        private fun pause() {
            //播放-》暂停
            mediaPlayer?.pause()
            EventBus.getDefault().post(list?.get(position))
            //更新图标
            notification?.contentView?.setImageViewResource(R.id.state,R.mipmap.btn_audio_pause_normal)
            manager?.notify(1,notification)
        }

        override fun isPlaying(): Boolean? {
            return mediaPlayer?.isPlaying
        }

        override fun onPrepared(mp: MediaPlayer?) {
            //播放音乐
            mediaPlayer?.start()
            //通知界面更新
            notifyUpdateUI()
            //显示通知界面
            showNotification()
        }

        /**
         * 显示通知
         */
        private fun showNotification() {
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notification = getNotification()
            manager?.notify(1,notification)
        }

        /**
         * 获取通知
         */
        private fun getNotification(): Notification? {

            val notification = NotificationCompat.Builder(this@AudioService)
                    .setTicker("正在播放${list?.get(position)?.displayName}")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setCustomContentView(getRemoteViews())
                    .setWhen(System.currentTimeMillis())
                    .setOngoing(true)//设置不能被滑动删除
                    .setContentIntent(getPendingIntent())
                    .build()
            return notification
        }

        private fun getPendingIntent(): PendingIntent? {
            val intentM = Intent(this@AudioService,MainActivity::class.java)
            val intentA = Intent(this@AudioService,AudioPlayerActivity::class.java)
            intentA.putExtra("from",FROM_CONTENT)
            val intents = arrayOf(intentM,intentA)
            val pendingIntent = PendingIntent.getActivities(this@AudioService,1,intents,PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        private fun getRemoteViews(): RemoteViews? {
            val remoteViews = RemoteViews(packageName,R.layout.notification)
            //修改标题和内容
            remoteViews.setTextViewText(R.id.title,list?.get(position)?.displayName)
            remoteViews.setTextViewText(R.id.artist,list?.get(position)?.artist)

            remoteViews.setOnClickPendingIntent(R.id.pre,getPrePendinIntent())
            remoteViews.setOnClickPendingIntent(R.id.state,getStatePendinIntent())
            remoteViews.setOnClickPendingIntent(R.id.next,getNextPendinIntent())

            return remoteViews
        }

        private fun getNextPendinIntent(): PendingIntent? {
            val intent = Intent(this@AudioService,AudioService::class.java)
            intent.putExtra("from",FROM_NEXT)
            val pendingIntent = PendingIntent.getService(this@AudioService,2,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        private fun getStatePendinIntent(): PendingIntent? {
            val intent = Intent(this@AudioService,AudioService::class.java)
            intent.putExtra("from",FROM_STATE)
            val pendingIntent = PendingIntent.getService(this@AudioService,3,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        private fun getPrePendinIntent(): PendingIntent? {
            val intent = Intent(this@AudioService,AudioService::class.java)
            intent.putExtra("from",FROM_PRE)
            val pendingIntent = PendingIntent.getService(this@AudioService,4,intent,PendingIntent.FLAG_UPDATE_CURRENT)
            return pendingIntent
        }

        /**
         * 通知页面更新
         */
        public fun notifyUpdateUI() {
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