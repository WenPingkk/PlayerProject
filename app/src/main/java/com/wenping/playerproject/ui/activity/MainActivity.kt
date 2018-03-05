package com.wenping.playerproject.ui.activity

import android.support.v7.widget.Toolbar
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.util.ToolBarManager
import org.jetbrains.anko.find

class MainActivity : BaseActivity() ,ToolBarManager{
    //惰性加载,toolbar 在用的时候才初始化:by lazy 线程安全哦!!!
    override val toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
        super.initData()
        initMainToolBar()
    }
}
