package com.wenping.playerproject.util

import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseFragment
import com.wenping.playerproject.ui.fragment.*

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
//私有化构造方法
class FragmentUtil private constructor(){

    //惰性加载
    val homeFragment by lazy { HomeFragment() }
    val mvFragment by lazy { MvFragment() }
    val vBangFragment by lazy { VBangFragment() }
    val yueDanFragment by lazy { YueDanFragment() }
    val settingFragment by lazy { SettingFragment() }
    companion object {
        val fragmentUtil by lazy {
            FragmentUtil()
        }
    }

    /**
     * 根据tabId获取fragment
     */
    fun getFragment(tabId: Int):BaseFragment? {

        when (tabId) {
            R.id.tab_home-> return homeFragment
            R.id.tab_vbang-> return vBangFragment
            R.id.tab_mv-> return mvFragment
            R.id.tab_yuedan-> return yueDanFragment
        }
    return null
    }

}