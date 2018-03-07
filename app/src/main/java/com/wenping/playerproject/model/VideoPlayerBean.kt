package com.wenping.playerproject.model

import android.os.Parcel
import android.os.Parcelable

/**
 * Author WenPing
 * CreateTime 2018/3/7.
 *传递给视频播放界面的数据 bean类
 */
data class VideoPlayerBean(var id:Int,var title:String,var url:String):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoPlayerBean> {
        override fun createFromParcel(parcel: Parcel): VideoPlayerBean {
            return VideoPlayerBean(parcel)
        }

        override fun newArray(size: Int): Array<VideoPlayerBean?> {
            return arrayOfNulls(size)
        }
    }

}