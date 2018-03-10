package com.wenping.playerproject.service

/**
 * @author WenPing
 * @date 2018/3/9
 * @decription:播放音乐,实现交互!
 *<p>
 */
interface Iservice {
    abstract fun updatePlayState()
    fun isPlaying():Boolean?
}