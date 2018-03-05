package com.wenping.playerproject.ui.fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.wenping.playerproject.R
import com.wenping.playerproject.adapter.HomeAdapter
import com.wenping.playerproject.base.BaseFragment
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

    override fun initView(): View? {
        return View.inflate(activity,R.layout.fragment_home,null)
    }

    override fun initListener() {
        //初始化recyclerview
       recyclerView.layoutManager = LinearLayoutManager(context)
        //适配
        val adapter = HomeAdapter()

        recyclerView.adapter =adapter
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
        client.newCall(request).enqueue(object :Callback{
            /**
             * 这两个回调都是在子线程中运行的
             */
            override fun onFailure(call: Call?, e: IOException?) {
                println("获取失败:"+Thread.currentThread())

            }

            override fun onResponse(call: Call?, response: Response?) {
                println("获取成功:"+Thread.currentThread())

            }

        })
    }
}