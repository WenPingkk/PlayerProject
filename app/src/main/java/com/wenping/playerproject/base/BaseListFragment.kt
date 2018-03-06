package com.wenping.playerproject.base

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wenping.playerproject.R
import com.wenping.playerproject.adapter.HomeAdapter
import com.wenping.playerproject.model.HomeItemBean
import com.wenping.playerproject.presenter.impl.HomePresenterImpl
import com.wenping.playerproject.view.HomeView
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * @author WenPing
 * @date 2018/3/6
 *<p>
 */
abstract class BaseListFragment<RESPONSE,ITEMBEAN,ITEMVIEW:View> :BaseFragment(), BaseView<RESPONSE> {
    //kotlin中特有的懒加载
    val adapter by lazy { getSpecialAdapter() }

    /**
     * 获取adapter
     */
    abstract fun getSpecialAdapter():BaseListAdapter<ITEMBEAN,ITEMVIEW>

    val presenter by lazy { getSpecialPresenter() }
    /**
     * 获取presenter
     */
    abstract fun getSpecialPresenter():BaseListPresenter

    override fun initView(): View? {
        return View.inflate(activity, R.layout.fragment_home, null)
    }

    override fun initListener() {
        //初始化recyclerview
        recyclerView.layoutManager = LinearLayoutManager(activity)
        //适配器
        recyclerView.adapter = adapter

        //设置颜色
        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLACK)
        //刷新监听
        refreshLayout.setOnRefreshListener {
            presenter.loadDatas()
        }

        //监听列表滑动
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            //监听:idle状态以及判断是否到了最后一条+1
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //只有 LinearLayoutManager的时recyclerview存在最后一条数据
                    val layoutManager = recyclerView.layoutManager
                    if (layoutManager is LinearLayoutManager) {
                        val manager: LinearLayoutManager = layoutManager
                        val lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                        if (lastVisibleItemPosition == adapter.itemCount - 1) {
                            //最后一条数据 加载更多数据;
                            //加载20条
                            presenter.loadMoreDatas(adapter.itemCount-1)

                        }
                    }
                }

            }
        })
    }

    override fun initData() {
        //初始化数据
        presenter.loadDatas()
    }

    override fun onError(message: String?) {
        //隐藏刷新控件
        refreshLayout.isRefreshing = false

        showToast("加载数据失败")
    }

    override fun loadSuccess(response: RESPONSE?) {
        //隐藏加载更多的控件
        refreshLayout.isRefreshing = false
        showToast("加载数据成功")
        //更新数据
        adapter.updateList(getSpecialList(response))
    }

    /**
     * 从返回结果中获取数据列表
     */
    abstract fun getSpecialList(response: RESPONSE?): List<ITEMBEAN>?

    override fun loadMore(response: RESPONSE?) {
        adapter.loadMore(getSpecialList(response))
    }

    /**
     * 解绑 view和presenter
     */
    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}