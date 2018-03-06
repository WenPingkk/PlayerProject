package com.wenping.playerproject.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wenping.playerproject.model.HomeItemBean
import com.wenping.playerproject.widget.HomeItemView
import com.wenping.playerproject.widget.LoadMoreView

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */class HomeAdapter : Adapter<HomeAdapter.HomeHolder>() {

    private var list = ArrayList<HomeItemBean>()

    //根据位置返回type值
    override fun getItemViewType(position: Int): Int {
        if (position == list.size) {
            //最后一条
            return 1
        } else {
            //普通条目
            return 0
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {

        if (viewType == 1)
            //最后一条
            return HomeHolder(LoadMoreView(parent.context))
         else
            //普通条目
            return HomeHolder(HomeItemView(parent.context))

    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        //如果是最后一条,不需要刷新view
        if (position == list.size) {
            return
        } else {
            //current条目
            val data = list.get(position)
            //条目的view
            val itemView = holder.itemView as HomeItemView
            //刷新条目
            itemView.setData(data)
        }
    }

    override fun getItemCount(): Int {
        return list.size + 1
    }

    class HomeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun updateList(list: List<HomeItemBean>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(list)
            //刷新页面!!!
            notifyDataSetChanged()
        }
    }
    /**
     *加载更多,不需要清空
     */
    fun loadMore(list: List<HomeItemBean>?) {
        list?.let {
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }
}
