package com.wenping.playerproject.net

import com.wenping.playerproject.model.HomeItemBean
import com.wenping.playerproject.util.URLProviderUtils

/**
 * @author WenPing
 * @date 2018/3/6
 *<p>
 */
class HomeRequest(type:Int,offset: Int, handler: ResponseHanlder<List<HomeItemBean>>) :
        MRequest<List<HomeItemBean>>(type,URLProviderUtils.getHomeUrl(offset, 20), handler) {
}