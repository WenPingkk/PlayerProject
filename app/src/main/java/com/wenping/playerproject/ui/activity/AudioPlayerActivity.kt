package com.wenping.playerproject.ui.activity

import android.util.Log
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.model.AudioBean

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
        val position = intent.getIntExtra("position",-1)
        Log.e("wen",list.toString())
        Log.e("wen",position.toString())
    }
}