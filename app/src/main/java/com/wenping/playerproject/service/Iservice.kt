package com.wenping.playerproject.service

import com.wenping.playerproject.model.AudioBean

/**
 * @author WenPing
 * @date 2018/3/9
 * @decription:播放音乐,实现交互!
 *<p>
 */
interface Iservice {
    abstract fun updatePlayState()
    fun isPlaying():Boolean?
    //获取总进度
    fun getDuration(): Int

    //获取当前进度
    fun getProgress(): Int

    fun seekTo(progress: Int)

    fun updatePlayMode()

    fun getPlayMode(): Int
    fun playPre()
    fun playNext()
    fun getPlayList(): List<AudioBean>?

    fun playPosition(position: Int)
}