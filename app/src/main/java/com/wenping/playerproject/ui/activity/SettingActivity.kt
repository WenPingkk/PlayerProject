package com.wenping.playerproject.ui.activity

import android.preference.PreferenceManager
import android.support.v7.widget.Toolbar
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.util.ToolBarManager
import org.jetbrains.anko.find

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
class SettingActivity : BaseActivity(),ToolBarManager {
    override val toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun initData() {
        super.initData()
        initSettingToolBar()
        val sp = PreferenceManager.getDefaultSharedPreferences(this)
        sp.getBoolean("push",false)
    }
}