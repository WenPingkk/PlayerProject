package com.wenping.playerproject.net

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wenping.playerproject.model.HomeItemBean
import java.lang.reflect.ParameterizedType

/**
 * @author WenPing
 * @date 2018/3/6
 * 所有的请求基类
 *<p>
 */
open class MRequest <RESPONSE>(val type:Int,val url:String,val handler:ResponseHanlder<RESPONSE>){
    //解析网络请求的结果
    fun parseRequest(result: String?): RESPONSE {
        val type = (this
                .javaClass
                .genericSuperclass
                as ParameterizedType).getActualTypeArguments()[0]

        //获取泛型类型
        val list = Gson().fromJson<RESPONSE>(result, type)
        return list
    }

    //发送网络请求
    fun execute() {
        NetManager.manager.sendRequest(this)
    }

}