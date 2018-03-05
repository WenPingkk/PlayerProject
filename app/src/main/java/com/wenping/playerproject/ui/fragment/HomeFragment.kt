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
import com.wenping.playerproject.util.ThreadUtil
import com.wenping.playerproject.util.URLProviderUtils
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
class HomeFragment : BaseFragment() {
    val adapter by lazy { HomeAdapter() }
    override fun initView(): View? {
        return View.inflate(activity, R.layout.fragment_home, null)
    }

    override fun initListener() {
        //初始化recyclerview
        recyclerView.layoutManager = LinearLayoutManager(context)
        //适配

        recyclerView.adapter = adapter

        //设置颜色
        refreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLACK)
        //刷新监听
        refreshLayout.setOnRefreshListener {
            //刷新的监听
            loadDatas()
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
                            loadMoreDatas(adapter.itemCount-1)

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
        loadDatas()
    }

    private fun loadDatas() {
        val path = URLProviderUtils.getHomeUrl(0, 20)
        //发送请求,获取数据
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(path)
                .get()
                .build()
        client.newCall(request).enqueue(object : Callback {
            /**
             * 这两个回调都是在子线程中运行的
             */
            override fun onFailure(call: Call?, e: IOException?) {
                ThreadUtil.runOnMainThread(object :Runnable{
                    override fun run() {
                        refreshLayout.isRefreshing =false
                    }
                })
                showToast("获取数据失败")
            }

            override fun onResponse(call: Call?, response: Response?) {
                ThreadUtil.runOnMainThread(object :Runnable{
                    override fun run() {
                        //隐藏刷新控件
                        refreshLayout.isRefreshing =false
                    }
                })

                val result = response?.body()?.string()
                val list = Gson().fromJson<List<HomeItemBean>>(result, object : TypeToken<List<HomeItemBean>>() {}.type)
                showToast("获取数据成功")
                println("获取成功:" + list.size)
                //刷新列表
                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
                        adapter.updateList(list)
                    }
                })
            }
        })
    }

    /**
     * 加载更多数据
     */
    private fun loadMoreDatas(offset:Int) {
        val path = URLProviderUtils.getHomeUrl(offset, 20)
        //发送请求,获取数据
        val client = OkHttpClient()
        val request = Request.Builder()
                .url(path)
                .get()
                .build()
        client.newCall(request).enqueue(object : Callback {
            /**
             * 这两个回调都是在子线程中运行的
             */
            override fun onFailure(call: Call?, e: IOException?) {
                ThreadUtil.runOnMainThread(object :Runnable{
                    override fun run() {
                        refreshLayout.isRefreshing =false
                    }
                })
                showToast("获取数据失败")
            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()
                val list = Gson().fromJson<List<HomeItemBean>>(result, object : TypeToken<List<HomeItemBean>>() {}.type)
                showToast("获取数据成功")
                println("获取成功:" + list.size)
                ThreadUtil.runOnMainThread(object :Runnable{
                    override fun run() {
                        //隐藏刷新控件
                        refreshLayout.isRefreshing =false
                        //刷新列表
                        adapter.loadMore(list)
                    }
                })
            }
        })
    }

}