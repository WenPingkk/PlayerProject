package com.wenping.playerproject.model

import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore

/**
 * @author WenPing
 * @date 2018/3/9
 * @decription:音乐列表条目bean类
 *<p>
 */
data class AudioBean(var data: String, var size: Long,
                     var displayName: String, var artist: String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readLong(),
            parcel.readString(),
            parcel.readString()) {
    }
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(data)
        parcel.writeLong(size)
        parcel.writeString(displayName)
        parcel.writeString(artist)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AudioBean> {
        override fun createFromParcel(parcel: Parcel): AudioBean {
            return AudioBean(parcel)
        }
        override fun newArray(size: Int): Array<AudioBean?> {
            return arrayOfNulls(size)
        }

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
        /**
         * 根据特定位置cursor获取整个播放列表
         */
        fun getAudioBeans(cursor: Cursor?): ArrayList<AudioBean> {
            //创建集合
            val list= ArrayList<AudioBean>()
            //判断cursor是否为空
            cursor?.let {
                //将cursor的游标一定到-1
                it.moveToPosition(-1)
                //解析cursor
                while (it.moveToNext()) {
                    val audioBean = getAudioBean(it)
                    list.add(audioBean)
                }
            }
            //解析cursor到集合中
            return list
        }
    }
}