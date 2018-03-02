package com.wenping.playerproject.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import org.jetbrains.anko.toast

/**
 * @author WenPing
 * @date 2018/3/2
 *<p>
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        initListener()

        initData()
    }

    /**
     * 初始化 数据
     */
    private fun initData() {
    }

    /**
     * 监听
     */
    protected fun initListener() {

    }

    //获取布局ID
    abstract fun getLayoutId(): Int


    protected fun showToast(msg: String) {
        //UI 线程
        runOnUiThread { toast(msg) }
    }
}