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
import com.wenping.playerproject.model.HomeItemBean
import com.wenping.playerproject.presenter.impl.HomePresenterImpl
import com.wenping.playerproject.util.ThreadUtil
import com.wenping.playerproject.util.URLProviderUtils
import com.wenping.playerproject.view.HomeView
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

/**
 * @author WenPing
 * @date 2018/3/5
 * 实现homeView接口,未实现的方法
 *<p>
 */
class HomeFragment : BaseFragment(), HomeView {
    //kotlin中特有的懒加载
    val adapter by lazy { HomeAdapter() }

    val presenter by lazy { HomePresenterImpl(this) }

    override fun initView(): View? {
        return View.inflate(activity, R.layout.fragment_home, null)
    }

    override fun initListener() {
        //初始化recyclerview
        recyclerView.layoutManager = LinearLayoutManager(activity)
        //适配器
        recyclerView.adapter = adapter

        //设置颜色
        refreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLACK)
        //刷新监听
        refreshLayout.setOnRefreshListener {
            //下拉刷新的监听
            presenter.loadDatas()
        }

        //监听列表滑动
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            //监听:idle状态以及判断是否到了最后一条+1
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                when (newState) {
//                    RecyclerView.SCROLL_STATE_IDLE->{
//                        println("SCROLL_STATE_IDLE")
//                    }
//                    RecyclerView.SCROLL_STATE_DRAGGING->{
//                        println("SCROLL_STATE_DRAGGING")
//                    }
//                    RecyclerView.SCROLL_STATE_SETTLING->{
//                        println("SCROLL_STATE_SETTLING")
//                    }
//                }

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
//            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
//                println("onScrolled")
//            }
        })
    }

    override fun initData() {
        //初始化数据
        presenter.loadDatas()
    }

    override fun onError(message: String?) {
        showToast("加载数据失败")
        //隐藏刷新控件
        refreshLayout.isRefreshing = false
    }

    override fun loadSuccess(list: List<HomeItemBean>?) {
        //隐藏加载更多的控件
        refreshLayout.isRefreshing = false
        //更新数据
        adapter.updateList(list)
    }

    override fun loadMore(list: List<HomeItemBean>?) {
        adapter.loadMore(list)
    }

    /**
     * 解绑 view和presenter
     */
    override fun onDestroy() {
        super.onDestroy()
        presenter.destroy()
    }
}