package com.wenping.playerproject.view

import com.wenping.playerproject.model.MvAreaBean

/**
 * @author WenPing
 * @date 2018/3/7
 *<p>
 */
interface MvView {
    fun onError(msg: String?)
    fun onSuccess(result: List<MvAreaBean>)
}