package com.wenping.playerproject.view

import com.wenping.playerproject.model.HomeItemBean
import com.wenping.playerproject.model.YueDanBean

/**
 * @author WenPing
 * @date 2018/3/6
 *<p>
 */
interface YueDanView {

    /**
     * 获取数据失败
     */
    fun onError(message: String?)

    /**
     * 获取数据成功
     */
    fun loadSuccess(response: YueDanBean?)

    /**
     * 加载更多数据
     */
    fun loadMore(response: YueDanBean?)
}

