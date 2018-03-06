package com.wenping.playerproject.presenter.impl

import com.wenping.playerproject.base.BaseListPresenter
import com.wenping.playerproject.base.BaseView
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
class YueDanPresenterImpl(var yueDanView: BaseView<YueDanBean>?) : YueDanPresenter, ResponseHanlder<YueDanBean> {
    override fun destroy() {
        if (yueDanView != null) {
            yueDanView = null
        }
    }

    override fun onError(type: Int, msg: String?) {
        yueDanView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: YueDanBean) {
        when (type) {
            BaseListPresenter.TYPE_INIT_OR_FRESH -> yueDanView?.loadSuccess(result)
            BaseListPresenter.TYPE_LOAD_MORE -> yueDanView?.loadMore(result)
        }
    }

    override fun loadDatas() {
        YueDanRequest(BaseListPresenter.TYPE_INIT_OR_FRESH, 0, this).execute()
    }

    override fun loadMoreDatas(i: Int) {
        YueDanRequest(BaseListPresenter.TYPE_LOAD_MORE, i, this).execute()
    }
}