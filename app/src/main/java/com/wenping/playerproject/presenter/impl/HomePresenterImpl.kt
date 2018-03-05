package com.wenping.playerproject.presenter.impl

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wenping.playerproject.model.HomeItemBean
import com.wenping.playerproject.presenter.inter.HomePresenter
import com.wenping.playerproject.util.ThreadUtil
import com.wenping.playerproject.util.URLProviderUtils
import com.wenping.playerproject.view.HomeView
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
//使用var 其他方法可直接使用 变量 homeView
// 和不使用var->其他方法不能使用变量
class HomePresenterImpl(var homeView: HomeView) :HomePresenter{
    override fun loadDatas() {
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
                        homeView.onError(e?.message)
                    }
                })

            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()
                val list = Gson().fromJson<List<HomeItemBean>>(result, object : TypeToken<List<HomeItemBean>>() {}.type)
                println("获取成功:" + list.size)
                //刷新列表
                ThreadUtil.runOnMainThread(object : Runnable {
                    override fun run() {
//                        /把结果返回给view层
                        homeView.loadSuccess(list)
                    }
                })
            }
        })
    }

    override fun loadMoreDatas(i: Int) {

        val path = URLProviderUtils.getHomeUrl(i, 20)
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
                        homeView.onError(e?.message)
                    }
                })
            }

            override fun onResponse(call: Call?, response: Response?) {
                val result = response?.body()?.string()
                val list = Gson().fromJson<List<HomeItemBean>>(result, object : TypeToken<List<HomeItemBean>>() {}.type)
                ThreadUtil.runOnMainThread(object :Runnable{
                    override fun run() {
                        homeView.loadMore(list)
                    }
                })
            }
        })
    }

    init {

    }


}