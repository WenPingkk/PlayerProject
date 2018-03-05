package com.wenping.playerproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.PreferenceScreen
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wenping.playerproject.R
import com.wenping.playerproject.ui.activity.AboutActivity
import org.jetbrains.anko.toast

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
class SettingFragment : PreferenceFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        addPreferencesFromResource(R.xml.setting)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onPreferenceTreeClick(preferenceScreen: PreferenceScreen?, preference: Preference?): Boolean {
        val key = preference?.key
        if ("about".equals(key)) {
            //点击了 关于界面
            toast("点击了关于界面")
            activity.startActivity(Intent(activity,AboutActivity::class.java))
        }
        return super.onPreferenceTreeClick(preferenceScreen, preference)
    }
}