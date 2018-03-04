package com.wenping.playerproject.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

/**
 * @author WenPing
 * @date 2018/3/2
 *<p>
 */
abstract class BaseActivity : AppCompatActivity(),AnkoLogger {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        initListener()

        initData()
        //debug { "haha" }
    }

    /**
     * 初始化 数据
     */
    open protected fun initData() {
    }

    /**
     * 监听
     */
    open protected fun initListener() {

    }

    //获取布局ID
    abstract fun getLayoutId(): Int



    protected fun showToast(msg: String) {
        //UI 线程
        runOnUiThread { toast(msg) }
    }

    /**
     * 开启一个activity并且finish it
     */
    inline fun <reified T:BaseActivity>startActivityAndFinish() {
        startActivity<T>()
        finish()
    }
}