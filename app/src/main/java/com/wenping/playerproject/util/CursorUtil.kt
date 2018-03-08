package com.wenping.playerproject.util

import android.database.Cursor

/**
 * @author WenPing
 * @date 2018/3/8
 * @decription:
 *<p>
 */
object CursorUtil {
    fun logCursor(cursor: Cursor?) {
        cursor?.let {
            //将cursor 游标移动到-1位
            it.moveToPosition(-1)
            while (it.moveToNext()) {
                for (index in 0 until it.columnCount) {
                    println("key=${it.getColumnName(index)}")
                }
            }
        }
    }
}