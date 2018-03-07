package com.wenping.playerproject.net

import com.wenping.playerproject.model.MvPagerBean
import com.wenping.playerproject.util.URLProviderUtils

/**
 * @author WenPing
 * @date 2018/3/7
 *<p>
 */
class MvListRequest(type:Int,code:String,offSet:Int,handler:ResponseHanlder<MvPagerBean>)
    : MRequest<MvPagerBean>(type,URLProviderUtils.getMVListUrl(code,offSet,20),handler) {

}