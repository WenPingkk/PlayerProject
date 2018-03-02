package com.wenping.playerproject.ui.activity

import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListener
import android.view.View
import com.wenping.playerproject.MainActivity
import com.wenping.playerproject.R
import com.wenping.playerproject.base.BaseActivity
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.startActivity

/**
 * Author WenPing
 * CreateTime 2018/3/2.
 *
 */
class SplashActivity: BaseActivity(), ViewPropertyAnimatorListener {


    override fun initData() {
        super.initData()
        //属性动画
        ViewCompat.animate(ivSplash).scaleX(1.0f).scaleY(1.0f).setDuration(2000).setListener(this)
    }

    override fun initListener() {
        super.initListener()

    }

    override fun onAnimationEnd(view: View?) {
        //这都是Anko库的操作
        startActivity<MainActivity>()
        finish()
    }

    override fun onAnimationCancel(view: View?) {
    }

    override fun onAnimationStart(view: View?) {

    }

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

}