package com.wenping.playerproject.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.wenping.playerproject.ui.fragment.DefaultFragment

/**
 * @author WenPing
 * @date 2018/3/8
 *<p>
 */
class VideoPagerAdapter(fm:FragmentManager?) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return DefaultFragment()
    }

    override fun getCount(): Int {
        return 3
    }
}