package com.wenping.playerproject.util

import android.os.Handler
import android.os.Looper

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
object ThreadUtil{

    val handler = Handler(Looper.getMainLooper())

    /**
     * 运行在子线程中
     */
    fun runOnMainThread(runnable: Runnable) {
        handler.post(runnable)
    }
}