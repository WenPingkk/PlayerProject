package com.wenping.playerproject.model

import android.database.Cursor
import android.provider.MediaStore

/**
 * @author WenPing
 * @date 2018/3/9
 * @decription:音乐列表条目bean类
 *<p>
 */
data class AudioBean(var data: String, var size: Long,
                     var displayName: String, var artist: String) {
    companion object {
        fun getAudioBean(cursor: Cursor?): AudioBean {
            /**
             * 根据特定的位置的cursor获取bean
             */
            //1.创建audioBean对象
            val audioBean = AudioBean("", 0, "", "")
            //2.判断cursor是否为空
            cursor?.let {
                audioBean.data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                audioBean.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE))
                audioBean.displayName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                audioBean.displayName = audioBean.displayName.substring(0, audioBean.displayName.lastIndexOf("."))
                audioBean.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST))
            }
            //3.解析cursor,并且设置到bean对象中
            return audioBean
        }
    }

}