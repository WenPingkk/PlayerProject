package com.wenping.playerproject.presenter.impl

import com.wenping.playerproject.model.YueDanBean
import com.wenping.playerproject.net.ResponseHanlder
import com.wenping.playerproject.net.YueDanRequest
import com.wenping.playerproject.presenter.inter.YueDanPresenter
import com.wenping.playerproject.view.YueDanView

/**
 * @author WenPing
 * @date 2018/3/6
 *<p>
 */
class YueDanPresenterImpl(var yueDanView: YueDanView) : YueDanPresenter, ResponseHanlder<YueDanBean> {
    override fun onSuccess(type: Int, response: YueDanBean) {
        when (type) {
            YueDanPresenter.TYPE_INIT_OR_FRESH -> yueDanView.loadSuccess(response)
            YueDanPresenter.TYPE_LOAD_MORE -> yueDanView.loadMore(response)
        }
    }

    override fun onError(type: Int, msg: String?) {
        yueDanView.onError(msg)
    }

    override fun loadDatas() {
        YueDanRequest(YueDanPresenter.TYPE_INIT_OR_FRESH, 0, this).execute()
    }

    override fun loadMoreDatas(i: Int) {
        YueDanRequest(YueDanPresenter.TYPE_LOAD_MORE, i - 1, this)
    }


}