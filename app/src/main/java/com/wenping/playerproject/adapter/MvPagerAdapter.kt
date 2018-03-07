package com.wenping.playerproject.adapter

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.wenping.playerproject.model.MvAreaBean
import com.wenping.playerproject.ui.fragment.MvPagerFragment

/**
 * @author WenPing
 * @date 2018/3/7
 *<p>
 */
class MvPagerAdapter(val context: Context?, val list:List<MvAreaBean>?, fm:FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
    //方法1:创建时传递数据 不能通过构造方法,通过 setarguement,bundle来传
//        val fragment = MvPagerFragment()
        val bundle = Bundle()
        bundle.putString("args",list?.get(position)?.code)
//        fragment.arguments = bundle
//        return fragment

        //方法2
        var fragment =  Fragment.instantiate(context, MvPagerFragment::class.java.name,bundle)
        return fragment
    }

    override fun getCount(): Int {
        //不为空返回 list.size,为空返回0
        return list?.size?:0
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return list?.get(position)?.name
    }

}