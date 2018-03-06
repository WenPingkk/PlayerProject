package com.wenping.playerproject.presenter.inter

/**
 * @author WenPing
 * @date 2018/3/6
 *<p>
 */
interface YueDanPresenter {

    //kotlin 通过companion来定义常量
    companion object {
        val TYPE_INIT_OR_FRESH :Int = 1
        val TYPE_LOAD_MORE :Int = 2
    }
    fun loadDatas()
    fun loadMoreDatas(i: Int)

}