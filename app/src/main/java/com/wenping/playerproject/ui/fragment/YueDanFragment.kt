package com.wenping.playerproject.ui.fragment

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.wenping.playerproject.R
import com.wenping.playerproject.adapter.YueDanAdapter
import com.wenping.playerproject.base.BaseFragment
import com.wenping.playerproject.model.YueDanBean
import com.wenping.playerproject.presenter.impl.YueDanPresenterImpl
import com.wenping.playerproject.view.YueDanView
import kotlinx.android.synthetic.main.fragment_yuedan.*

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
class YueDanFragment : BaseFragment(), YueDanView {

    override fun onError(message: String?) {
        showToast("加载数据失败")
    }

    override fun loadSuccess(response: YueDanBean?) {
        //刷新adapter
        adapter.updateData(response?.playLists)
    }

    override fun loadMore(response: YueDanBean?) {

    }

    val adapter by lazy { YueDanAdapter() }

    val presenter by lazy { YueDanPresenterImpl(this) }
    override fun initListener() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

    }

    override fun initView(): View? {
        return View.inflate(activity,R.layout.fragment_yuedan,null)
    }

    override fun initData() {
        //加载数据
        presenter.loadDatas()
    }
}