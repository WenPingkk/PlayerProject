package com.wenping.playerproject.base

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wenping.playerproject.widget.LoadMoreView

/**
 * @author WenPing
 * @date 2018/3/6
 *<p>
 */
/**
 * 所有下拉刷新和上拉加载更多列表界面 adapter 的基类
 */
abstract class BaseListAdapter<ITEMBEAN,ITEMVIEW:View>: RecyclerView.Adapter<BaseListAdapter.BaseListHolder>() {

    private var list = ArrayList<ITEMBEAN>()

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseListHolder {

        if (viewType == 1)
        //最后一条
            return BaseListHolder(LoadMoreView(parent?.context))
        else
        //普通条目
            return BaseListHolder(getItemView(parent?.context))

    }

    override fun onBindViewHolder(holder: BaseListHolder, position: Int) {
        //如果是最后一条,不需要刷新view
        if (position == list.size) {
            return
        } else {
            //current条目
            val data = list.get(position)
            //条目的view
            val itemView = holder.itemView as ITEMVIEW
            //刷新条目
            refreshItemView(itemView,data)
            itemView.setOnClickListener {
//                var a:Int = 0
//                var b:Int = 9
//                var c = b/a
                Toast.makeText(it.context,"Position:$position",Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun getItemCount(): Int {
        return list.size + 1
    }

    class BaseListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun updateList(list: List<ITEMBEAN>?) {
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
    fun loadMore(list: List<ITEMBEAN>?) {
        list?.let {
            this.list.addAll(list)
            notifyDataSetChanged()
        }
    }

    /**
     * 刷新条目 view
     */
    abstract fun getItemView(context: Context?): ITEMVIEW

    /**
     * 获取当前的itemView
     */
    abstract fun refreshItemView(itemView: ITEMVIEW, data: ITEMBEAN)
}