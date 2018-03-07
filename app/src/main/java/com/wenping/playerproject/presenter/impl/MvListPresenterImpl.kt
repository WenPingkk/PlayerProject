package com.wenping.playerproject.presenter.impl

import com.wenping.playerproject.base.BaseListPresenter
import com.wenping.playerproject.model.MvPagerBean
import com.wenping.playerproject.net.MvListRequest
import com.wenping.playerproject.net.ResponseHanlder
import com.wenping.playerproject.presenter.inter.MvListPresenter
import com.wenping.playerproject.view.MvListView

/**
 * @author WenPing
 * @date 2018/3/7
 *<p>
 */
class MvListPresenterImpl(var code:String, var mvListView:MvListView?):MvListPresenter, ResponseHanlder<MvPagerBean> {
    override fun onError(type: Int, msg: String?) {
        mvListView?.onError(msg)
    }

    override fun onSuccess(type: Int, result: MvPagerBean) {
        when (type) {
            BaseListPresenter.TYPE_INIT_OR_FRESH-> mvListView?.loadSuccess(result)
            BaseListPresenter.TYPE_LOAD_MORE-> mvListView?.loadMore(result)
        }
    }

    override fun loadDatas() {
        MvListRequest(BaseListPresenter.TYPE_INIT_OR_FRESH, code, 0, this)
    }

    override fun loadMoreDatas(i: Int) {
        MvListRequest(BaseListPresenter.TYPE_LOAD_MORE,code,i,this)
    }

    override fun destroy() {
        if (mvListView != null) {
            mvListView = null
        }
    }
}