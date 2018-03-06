package com.wenping.playerproject.net

import com.wenping.playerproject.model.YueDanBean
import com.wenping.playerproject.util.URLProviderUtils

/**
 * @author WenPing
 * @date 2018/3/6
 *<p>
 */
class YueDanRequest(type:Int,offSet:Int,handler:ResponseHanlder<YueDanBean>) : MRequest<YueDanBean>(type,URLProviderUtils.getYueDanUrl(offSet,20),handler) {

}