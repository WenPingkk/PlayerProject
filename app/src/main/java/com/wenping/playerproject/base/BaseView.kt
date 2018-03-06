package com.wenping.playerproject.base

import com.wenping.playerproject.model.HomeItemBean

/**
 * @author WenPing
 * @date 2018/3/6
 * 所有下拉刷新和上拉加载更多页面view的接口
 *<p>
 */
/**
 * homeView->BaseView
 * presenter->BaseListPresenter
 * adapter ->BaseListAdapter
 */
interface BaseView<RESPONSE> {



    /**
     * 获取数据失败
     */
    fun onError(message: String?)

    /**
     * 获取数据成功
     */
    fun loadSuccess(list: RESPONSE?)

    /**
     * 加载更多数据
     */
    fun loadMore(list: RESPONSE?)


}