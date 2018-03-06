package com.wenping.playerproject.net

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wenping.playerproject.model.HomeItemBean
import com.wenping.playerproject.util.ThreadUtil
import com.wenping.playerproject.util.URLProviderUtils
import okhttp3.*
import java.io.IOException

/**
 * @author WenPing
 * @date 2018/3/6
 * 发送网络请求类
 *<p>
 */
class NetManager private constructor(){
    //发送请求,获取数据
    val client by lazy { OkHttpClient() }
    companion object {
        val manager by lazy { NetManager() }
    }

    /**
     * 发送网络请求
     */
    fun<RESPONSE> sendRequest(req:MRequest<RESPONSE>) {

        val request = Request.Builder()
                .url(req.url)
                .get()
                .build()
        client.newCall(request).enqueue(object : Callback {
            /**
             * 这两个回调都是在子线程中运行的
             */
            override fun onFailure(call: Call?, e: IOException?) {
                ThreadUtil.runOnMainThread(object :Runnable{
                    override fun run() {
                        req.handler.onError(req.type,e?.message)
                    }
                })
            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()
                val parseResult = req.parseRequest(result)
                ThreadUtil.runOnMainThread(object :Runnable{
                    override fun run() {
                        req.handler.onSuccess(req.type,parseResult)
                    }
                })
            }
        })
    }
}