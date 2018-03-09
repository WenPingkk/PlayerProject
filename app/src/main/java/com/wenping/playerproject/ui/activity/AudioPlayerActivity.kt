package com.wenping.playerproject.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.model.AudioBean
import com.wenping.playerproject.service.AudioService
import com.wenping.playerproject.service.Iservice
import java.sql.Connection

/**
 * @author WenPing
 * @date 2018/3/9
 * @decription:
 *<p>
 */
class AudioPlayerActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_audio_player
    }

    override fun initData() {
        val list = intent.getParcelableArrayListExtra<AudioBean>("list")
        val position = intent.getIntExtra("position", -1)

//        val mediaPlayer =MediaPlayer()
//        mediaPlayer.setDataSource(list.get(position).data)
//        mediaPlayer.prepareAsync()
//        mediaPlayer.setOnPreparedListener {
//            mediaPlayer.start()

        //通过AudioService播放音乐;开启服务
        //混合启动的方式
        val intent = Intent(this, AudioService::class.java)
        //开启服务
        intent.putExtra("list",list)
        intent.putExtra("position",position)
        startService(intent)
        //绑定服务
        bindService(intent,conn,Context.BIND_AUTO_CREATE)

    }
    val conn by lazy { AudioConnection() }
    /**
     * 创建 ServiceConnection
     */

    //var iServcie:Iservice? = null

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
            //iServcie = service as Iservice

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //手动解绑服务
        unbindService(conn)
    }
}