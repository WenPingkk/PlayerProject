package com.wenping.playerproject.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wenping.playerproject.model.YueDanBean
import com.wenping.playerproject.widget.LoadMoreView
import com.wenping.playerproject.widget.YueDanItemView

/**
 * @author WenPing
 * @date 2018/3/6
 * 悦单界面的adapter
 *<p>
 */
class YueDanAdapter : RecyclerView.Adapter<YueDanAdapter.YueDanHolder>() {

    private var list = ArrayList<YueDanBean.PlayListsBean>();

    //刷新adapter数据
    fun updateData(list:List<YueDanBean.PlayListsBean>?) {
        list?.let {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == list.size) {
            //最后一条,刷新控件
            return 1;
        } else {
            //显示数据条目 控件
            return 0;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YueDanHolder {

        if (viewType == 1) {
            //刷新控件
            return YueDanHolder(LoadMoreView(parent?.context))
        } else {
            //普通条目
            return YueDanHolder(YueDanItemView(parent?.context))
        }

        return YueDanHolder(YueDanItemView(parent?.context))
    }



    override fun getItemCount(): Int {
        return list.size+1
    }

    override fun onBindViewHolder(holder: YueDanHolder, position: Int) {
        if (position == list.size)
            return
        else
        {
            //data
            val data: YueDanBean.PlayListsBean = list.get(position)
            //itemView
            val itemView = holder?.itemView as YueDanItemView
            //view 和数据绑定
            itemView.setData(data)
        }
    }


    class YueDanHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

    fun loadMore(list:List<YueDanBean.PlayListsBean>?) {
        list?.let {
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

}