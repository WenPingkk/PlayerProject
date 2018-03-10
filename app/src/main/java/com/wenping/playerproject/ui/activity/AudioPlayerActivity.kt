package com.wenping.playerproject.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.view.View
import android.widget.SeekBar
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.model.AudioBean
import com.wenping.playerproject.service.AudioService
import com.wenping.playerproject.service.Iservice
import com.wenping.playerproject.util.StringUtil
import de.greenrobot.event.EventBus
import kotlinx.android.synthetic.main.activity_music_player_bottom.*
import kotlinx.android.synthetic.main.activity_music_player_middle.*
import kotlinx.android.synthetic.main.activity_music_player_top.*

/**
 * @author WenPing
 * @date 2018/3/9
 * @decription:
 *<p>
 */
class AudioPlayerActivity : BaseActivity(), View.OnClickListener {
    var audioBean:AudioBean?=null
    var drawable:AnimationDrawable? = null
    var duration:Int = 0
    //静态的方法:companion object
//    companion object {
        val handler = object : Handler() {
            override fun handleMessage(msg: Message?) {
                when (msg?.what) {
                    MSG_PROGRESS-> startUpdateProgress()
                }
            }
        }
//    }
    val MSG_PROGRESS = 0
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.state -> updatePlayState()
        }
    }

    /**
     * 接收eventbus方法；参数匹配！
     */
    fun onEventMainThread(itemBean:AudioBean) {
        //记录播放歌曲bean
        this.audioBean = itemBean
        // 歌曲名称
        audio_title.text = itemBean.displayName
        //歌手名称，
        artist.text = itemBean.artist
        //更新播放状态按钮
        updatePlayStateBtn()
        //动画播放
        drawable = audio_anim.drawable as AnimationDrawable
        drawable?.start()

        //更新播放进度
        //当前进度以及总进度
        //1.获取总进度
        duration = iServcie?.getDuration()?:0
        //设置进度最大值
        progress_sk.max = duration
        //2.显示当前进度
        startUpdateProgress()


    }
    //显示当前的进度
    private fun startUpdateProgress() {
        //开始更新进度
        //获取当前进度2
        val progress:Int = iServcie?.getProgress()?:0
        //更新进度数据;界面和功能分开
        updateProgress(progress)
        //定时获取进度
        handler.sendEmptyMessageDelayed(MSG_PROGRESS,1000)
    }

    /**
     * 根据档期那数据更新界面
     */
    private fun updateProgress(pro:Int) {
        //更新进度数值
        progress.text = StringUtil.parseDuration(pro)+
                "/"+StringUtil.parseDuration(duration)
        //更新进度
        progress_sk.setProgress(pro)
    }


    /**
     * 更新播放状态
     */
    private fun updatePlayState() {
        //更新播放的状态；操作service，需要用到iServcie接口
        iServcie?.updatePlayState()
        //更新播放状态的图标
        updatePlayStateBtn()
    }

    /**
     * 根据当前播放状态来更新图标
     */
    private fun updatePlayStateBtn() {
        //获取档期那播放状态
        //根据状态更新图标
        val isPlaying = iServcie?.isPlaying()
        isPlaying?.let {
            if (isPlaying) {
                //播放
                state.setImageResource(R.drawable.selector_btn_audio_play)
                drawable?.start()
                //开始 handler 发送信息 定时跟新
                handler.sendEmptyMessage(MSG_PROGRESS)
            } else {
                //暂停'
                state.setImageResource(R.drawable.selector_btn_audio_pause)
                drawable?.stop()
                //停止 handler发送信息 停止定时更新
                handler.removeMessages(MSG_PROGRESS)
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    override fun initListener() {
        super.initListener()
        state.setOnClickListener (this)
        back.setOnClickListener{
            finish()
        }
        //进度条设置监听
        progress_sk.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            /**进度改变
             * progress： 改变之后的进度
             * fromUser：true-》用户手指拖动改变的；false-》代码的方式改变进度
             */
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //判断是否是用户自己操作造成的进度改变
                if (fromUser) {
                    //更新播放进度
                    iServcie?.seekTo(progress)
                    //更新界面进度显示
                    updateProgress(progress)
                }
            }

            /**
             * 手指触摸seekbar的回调
             */
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

    }

    override fun initData() {

        //注册eventbus
        EventBus.getDefault().register(this)


        //val list = intent.getParcelableArrayListExtra<AudioBean>("list")
        //val position = intent.getIntExtra("position", -1)

//        val mediaPlayer =MediaPlayer()
//        mediaPlayer.setDataSource(list.get(position).data)
//        mediaPlayer.prepareAsync()
//        mediaPlayer.setOnPreparedListener {
//            mediaPlayer.start()

        //通过AudioService播放音乐;开启服务
        //混合启动的方式
        val intent = intent;
        intent.setClass(this,AudioService::class.java)
        //开启服务
        //intent.putExtra("list",list)
        //intent.putExtra("position",position)
        startService(intent)
        //绑定服务
        bindService(intent,conn,Context.BIND_AUTO_CREATE)

    }
    val conn by lazy { AudioConnection() }
    /**
     * 创建 ServiceConnection
     */

    var iServcie:Iservice? = null

    inner class AudioConnection:ServiceConnection{
        /**
         * 意外断开连接的情况
         */
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        /**
         * 连接时
         */
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            iServcie = service as Iservice
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //手动解绑服务
        unbindService(conn)
        //eventbus 反注册
        EventBus.getDefault().unregister(this)
        //清空handler发送的所有消息-》防止出现内存泄露
        handler.removeCallbacksAndMessages(null)
    }
}