package com.wenping.playerproject.base.tinker

import com.tencent.tinker.loader.app.DefaultApplicationLike
import android.app.Application
import android.os.Build
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.support.multidex.MultiDex
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.crashreport.CrashReport
import com.wenping.playerproject.BuildConfig


/**
 * @author WenPing
 * @date 2018/3/7
 *<p>
 */
/**
 * Application application, int tinkerFlags,
boolean tinkerLoadVerifyFlag, long applicationStartElapsedTime,
long applicationStartMillisTime, Intent tinkerResultIntent
 */
class SampleApplicationLike( application:Application,  tinkerFlags:Int,
                             tinkerLoadVerifyFlag:Boolean,  applicationStartElapsedTime:Long,
                             applicationStartMillisTime:Long,  tinkerResultIntent: Intent) : DefaultApplicationLike(application
,tinkerFlags,tinkerLoadVerifyFlag,applicationStartElapsedTime,applicationStartMillisTime,tinkerResultIntent) {

    override fun onCreate() {
        super.onCreate()
        CrashReport.initCrashReport(application, "1b85e4f665", BuildConfig.runBugly);
        // 这里实现SDK初始化，appId替换成你的在Bugly平台申请的appId
        // 调试时，将第三个参数改为true
        Bugly.init(application, "1b85e4f665", true)
    }


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    override fun onBaseContextAttached(base: Context) {
        super.onBaseContextAttached(base)
        // you must install multiDex whatever tinker is installed!
        MultiDex.install(base)

        // 安装tinker
        // TinkerManager.installTinker(this); 替换成下面Bugly提供的方法
        Beta.installTinker(this)
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun registerActivityLifecycleCallback(callbacks: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callbacks)
    }
}