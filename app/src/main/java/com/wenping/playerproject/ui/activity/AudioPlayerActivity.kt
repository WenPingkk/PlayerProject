package com.wenping.playerproject.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.graphics.drawable.AnimationDrawable
import android.os.IBinder
import android.view.View
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.model.AudioBean
import com.wenping.playerproject.service.AudioService
import com.wenping.playerproject.service.Iservice
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
            } else {
                //暂停'
                state.setImageResource(R.drawable.selector_btn_audio_pause)
                drawable?.stop()
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
    }
}