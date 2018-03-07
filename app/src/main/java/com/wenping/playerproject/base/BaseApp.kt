package com.wenping.playerproject.base

import android.app.Application
import com.tencent.bugly.crashreport.CrashReport
import com.tencent.tinker.loader.app.TinkerApplication
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.wenping.playerproject.BuildConfig

/**
 * @author WenPing
 * @date 2018/3/7
 *<p>
 */
class BaseApp : TinkerApplication(ShareConstants.TINKER_ENABLE_ALL, "com.wenping.playerproject.base.tinker.SampleApplicationLike",
        "com.tencent.tinker.loader.TinkerLoader", false) {

}