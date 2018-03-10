package com.wenping.playerproject.util

/**
 * Author WenPing
 * CreateTime 2018/3/10.
 * Description:处理时间
 */
object StringUtil {
    val HOUR = 60*60*1000
    val MINUTE = 60*1000
    val SECOND = 1000

    fun parseDuration(progress: Int):String{
        val hour = progress/ HOUR
        val min = progress% HOUR/ MINUTE
        val sec = progress% HOUR% MINUTE/ SECOND
        var result:String =""
        //不足一小时 不显示小时
        if (hour == 0) {//百分号 不足两个以0补齐
            result = String.format("%02d:%02d",min,sec)
        } else {
            result = String.format("%02d:%02d:%02d",hour,min,sec)
        }
        return result
    }
}