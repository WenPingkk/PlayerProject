package com.wenping.playerproject.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.wenping.playerproject.model.YueDanBean
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YueDanHolder {
        return YueDanHolder(YueDanItemView(parent?.context))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: YueDanHolder, position: Int) {
        //data
        val data: YueDanBean.PlayListsBean = list.get(position)
        //itemView
        val itemView = holder?.itemView as YueDanItemView
        //view 和数据绑定
        itemView.setData(data)
    }


    class YueDanHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    }

}