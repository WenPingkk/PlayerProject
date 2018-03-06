package com.wenping.playerproject.ui.fragment

import android.graphics.Color
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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
        refreshLayout.isRefreshing = false
    }

    override fun loadSuccess(response: YueDanBean?) {
        refreshLayout.isRefreshing = false
        //刷新adapter
        adapter.updateData(response?.playLists)
    }

    override fun loadMore(response: YueDanBean?) {
        //刷新列表:增加
        adapter.loadMore(response?.playLists)
    }

    val adapter by lazy { YueDanAdapter() }

    val presenter by lazy { YueDanPresenterImpl(this) }
    override fun initListener() {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        //初始化刷新控件
        refreshLayout.setColorSchemeColors(Color.RED,Color.GREEN,Color.BLACK)
        //监听刷新控件
        refreshLayout.setOnRefreshListener {
            presenter.loadDatas()
        }
        //监听刷新控件:上拉加载更多
        recyclerView.addOnScrollListener(object :RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val layoutManager = recyclerView?.layoutManager
                    if (!(layoutManager is LinearLayoutManager)) return
                    //显示列表;kotlin的智能类型转换->不需要cast
                    val lastPos = layoutManager.findLastVisibleItemPosition();
                    if (lastPos == adapter.itemCount - 1) {
                        //加载更多,已经显示
                        presenter.loadMoreDatas(adapter.itemCount-1)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }
        })


    }

    override fun initView(): View? {
        return View.inflate(activity,R.layout.fragment_yuedan,null)
    }

    override fun initData() {
        //加载数据
        presenter.loadDatas()
    }


}