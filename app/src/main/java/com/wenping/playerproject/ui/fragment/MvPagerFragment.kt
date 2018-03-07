package com.wenping.playerproject.ui.fragment

import com.wenping.playerproject.adapter.MvListAdapter
import com.wenping.playerproject.base.BaseListAdapter
import com.wenping.playerproject.base.BaseListFragment
import com.wenping.playerproject.base.BaseListPresenter
import com.wenping.playerproject.model.MvPagerBean
import com.wenping.playerproject.model.VideosBean
import com.wenping.playerproject.presenter.impl.MvListPresenterImpl
import com.wenping.playerproject.view.MvListView
import com.wenping.playerproject.widget.MvItemView

/**
 * @author WenPing
 * @date 2018/3/7
 *<p>
 */
class MvPagerFragment : BaseListFragment<MvPagerBean, VideosBean, MvItemView>(), MvListView {
    var code:String? = null
    override fun init() {
        code = arguments?.getString("args")
    }

    override fun getSpecialAdapter(): BaseListAdapter<VideosBean, MvItemView> {
        return MvListAdapter()
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return MvListPresenterImpl(code!!,this)
    }

    override fun getSpecialList(response: MvPagerBean?): List<VideosBean>? {
        return response?.videos
    }
}