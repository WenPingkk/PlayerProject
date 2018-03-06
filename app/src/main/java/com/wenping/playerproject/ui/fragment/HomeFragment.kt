package com.wenping.playerproject.ui.fragment

import com.wenping.playerproject.adapter.HomeAdapter
import com.wenping.playerproject.base.BaseListAdapter
import com.wenping.playerproject.base.BaseListFragment
import com.wenping.playerproject.base.BaseListPresenter
import com.wenping.playerproject.model.HomeItemBean
import com.wenping.playerproject.presenter.impl.HomePresenterImpl
import com.wenping.playerproject.widget.HomeItemView

/**
 * @author WenPing
 * @date 2018/3/5
 * 实现homeView接口,未实现的方法
 *<p>
 */
class HomeFragment : BaseListFragment<List<HomeItemBean>, HomeItemBean, HomeItemView>() {

    override fun getSpecialAdapter(): BaseListAdapter<HomeItemBean, HomeItemView> {
        return HomeAdapter()
    }

    override fun getSpecialPresenter(): BaseListPresenter {
        return HomePresenterImpl(this)
    }

    override fun getSpecialList(response: List<HomeItemBean>?): List<HomeItemBean>?{
        return response
    }
}