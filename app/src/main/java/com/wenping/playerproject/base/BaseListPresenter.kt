package com.wenping.playerproject.base

/**
 * @author WenPing
 * @date 2018/3/6
 *<p>
 */
/**
 * 所有下拉刷新和上拉加载更多列表页面presenter的基类
 */
interface BaseListPresenter {
    //kotlin 通过companion来定义常量
    companion object {
        val TYPE_INIT_OR_FRESH :Int = 1
        val TYPE_LOAD_MORE :Int = 2
    }
    fun loadDatas()
    fun loadMoreDatas(i: Int)

    //解绑presenter和view层
    fun destroy()

}