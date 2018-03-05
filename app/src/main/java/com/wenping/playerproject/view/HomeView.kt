package com.wenping.playerproject.view

import com.wenping.playerproject.model.HomeItemBean

/**
 * @author WenPing
 * @date 2018/3/5
 * 负责home和precenter交互
 *<p>
 */
interface HomeView{
    /**
     * 获取数据失败
     */
    fun onError(message: String?)

    /**
     * 获取数据成功
     */
    fun loadSuccess(list: List<HomeItemBean>?)

    /**
     * 加载更多数据
     */
    fun loadMore(list: List<HomeItemBean>?)


}
