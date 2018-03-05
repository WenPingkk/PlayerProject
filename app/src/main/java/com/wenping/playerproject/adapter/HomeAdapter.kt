package com.wenping.playerproject.adapter

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.View
import android.view.ViewGroup
import com.wenping.playerproject.widget.HomeItemView

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
class HomeAdapter : Adapter<HomeAdapter.HomeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(HomeItemView(parent.context))
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 20
    }

    class HomeHolder(itemView :View) : RecyclerView.ViewHolder(itemView) {

    }

}