package com.wenping.playerproject.presenter.impl

import com.wenping.playerproject.model.MvAreaBean
import com.wenping.playerproject.net.MvAreaRequest
import com.wenping.playerproject.net.ResponseHanlder
import com.wenping.playerproject.presenter.inter.MvPresenter
import com.wenping.playerproject.view.MvView

/**
 * @author WenPing
 * @date 2018/3/7
 *<p>
 */
class MvPresenterImpl(var mvView:MvView) :MvPresenter, ResponseHanlder<List<MvAreaBean>> {
    override fun onError(type: Int, msg: String?) {
        mvView.onError(msg)
    }

    override fun onSuccess(type: Int, result: List<MvAreaBean>) {
        mvView.onSuccess(result)
    }

    override fun loadDatas() {
        MvAreaRequest(this).execute()
    }

}