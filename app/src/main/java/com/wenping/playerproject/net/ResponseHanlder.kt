package com.wenping.playerproject.net

/**
 * @author WenPing
 * @date 2018/3/6
 * 请求回调
 *<p>
 */
interface ResponseHanlder<RESPONSE>{

    fun onError(type:Int,msg:String?)

    fun onSuccess(type: Int,result:RESPONSE)
}