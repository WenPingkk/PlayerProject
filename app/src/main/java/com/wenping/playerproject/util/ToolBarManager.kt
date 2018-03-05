package com.wenping.playerproject.util

import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.Toast
import com.wenping.playerproject.R
import com.wenping.playerproject.ui.activity.SettingActivity


/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
interface ToolBarManager {

    val toolbar: Toolbar

    /**
     * 初始化mainAct中的toolbar
     */
    fun initMainToolBar() {
        //SET TITLE
        toolbar.setTitle("kk影音")
        //inflate 菜单布局
        toolbar.inflateMenu(R.menu.menu)

        //kotlin和java之间调用的特性
        //OnMenuItemClickListener 接口中只有一个未实现的方法;可以把接口省略掉->大括号打开
        //如果java接口中只有一个未实现的方法,可以省略接口对象,直接复写这个方法
        toolbar.setOnMenuItemClickListener {
            //println("item=$it")
            toolbar.context.startActivity(Intent(toolbar.context, SettingActivity::class.java))
            true
        }

        //点击效果
//        toolbar.setOnMenuItemClickListener(object :Toolbar.OnMenuItemClickListener{
//            override fun onMenuItemClick(item: MenuItem?): Boolean {
//
//                when (item?.itemId) {
//                    R.id.setting->{
//                        //Toast.makeText(toolbar.context,"点击了设置按钮",Toast.LENGTH_SHORT).show()
//                        //跳转到setting页面
//                        //kotlin 的:: class ;java的::class.java
//                        toolbar.context.startActivity(Intent(toolbar.context,SettingActivity::class.java))
//                    }
//                }
//                //true 消费事件
//                return true
//            }
//        })
    }

    /**
     * 处理设置界面的toolbar
     */
    fun initSettingToolBar(){
        toolbar.setTitle("设置界面")
    }

}