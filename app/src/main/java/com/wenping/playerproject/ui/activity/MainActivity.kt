package com.wenping.playerproject.ui.activity

import android.support.v7.widget.Toolbar
import com.roughike.bottombar.OnTabReselectListener
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import com.wenping.playerproject.util.FragmentUtil
import com.wenping.playerproject.util.ToolBarManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

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

    override fun initListener() {
        //tab 切换的监听
        bottomBar.setOnTabSelectListener {
            //it:代表tabId
            val transaction = supportFragmentManager.beginTransaction();
            toast("it:$it")//一开始的it为一串数字,是的默认tabId,第一个tab,后面的tabId依次加1
            transaction.replace(R.id.container,FragmentUtil.fragmentUtil.getFragment(it),it.toString())
            transaction.commit()
        }
    }
}
