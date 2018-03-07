package com.wenping.playerproject.net

import com.wenping.playerproject.model.MvAreaBean
import com.wenping.playerproject.util.URLProviderUtils


/**
 * @author WenPing
 * @date 2018/3/7
 * mv:区域数据请求
 *<p>
 */
class MvAreaRequest (handler:ResponseHanlder<List<MvAreaBean>>)
    : MRequest<List<MvAreaBean>>(0,URLProviderUtils.getMVareaUrl(),handler) {
}