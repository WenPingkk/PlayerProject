package com.wenping.playerproject.ui.fragment

import android.view.View
import com.wenping.playerproject.R
import com.wenping.playerproject.adapter.MvPagerAdapter
import com.wenping.playerproject.base.BaseFragment
import com.wenping.playerproject.model.MvAreaBean
import com.wenping.playerproject.presenter.impl.MvPresenterImpl
import com.wenping.playerproject.view.MvView
import kotlinx.android.synthetic.main.fragment_mv.*

/**
 * @author WenPing
 * @date 2018/3/5
 *<p>
 */
class MvFragment : BaseFragment(), MvView {
    override fun onError(msg: String?) {
        showToast("加载区域数据失败")
    }

    override fun onSuccess(result: List<MvAreaBean>) {

        //管理fragment需要用childfragmentmanager
        val adapter = MvPagerAdapter(context,result,childFragmentManager)
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    val presenter by lazy { MvPresenterImpl(this) }

    override fun initView(): View? {
        return View.inflate(context, R.layout.fragment_mv, null)

    }

    override fun initData() {
        presenter.loadDatas()
    }

    override fun initListener() {

    }
}