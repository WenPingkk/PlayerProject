package com.wenping.playerproject.ui.fragment

import com.wenping.playerproject.adapter.YueDanAdapter
import com.wenping.playerproject.base.BaseListAdapter
import com.wenping.playerproject.base.BaseListFragment
import com.wenping.playerproject.base.BaseListPresenter
import com.wenping.playerproject.model.YueDanBean
import com.wenping.playerproject.presenter.impl.YueDanPresenterImpl
import com.wenping.playerproject.view.YueDanView
import com.wenping.playerproject.widget.YueDanItemView

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
class YueDanFragment : BaseListFragment<YueDanBean, YueDanBean.PlayListsBean, YueDanItemView>(), YueDanView {
    override fun getSpecialAdapter(): BaseListAdapter<YueDanBean.PlayListsBean, YueDanItemView> {
        return YueDanAdapter()
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return YueDanPresenterImpl(this)
    }

    override fun getSpecialList(response: YueDanBean?): List<YueDanBean.PlayListsBean>? {
        return response?.playLists;
    }
}