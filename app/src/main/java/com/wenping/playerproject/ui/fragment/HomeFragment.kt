package com.wenping.playerproject.ui.fragment

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wenping.playerproject.R
import com.wenping.playerproject.adapter.HomeAdapter
import com.wenping.playerproject.base.BaseFragment
import com.wenping.playerproject.base.BaseListAdapter
import com.wenping.playerproject.base.BaseListFragment
import com.wenping.playerproject.base.BaseListPresenter
import com.wenping.playerproject.model.HomeItemBean
import com.wenping.playerproject.presenter.impl.HomePresenterImpl
import com.wenping.playerproject.util.ThreadUtil
import com.wenping.playerproject.util.URLProviderUtils
import com.wenping.playerproject.view.HomeView
import com.wenping.playerproject.widget.HomeItemView
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

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