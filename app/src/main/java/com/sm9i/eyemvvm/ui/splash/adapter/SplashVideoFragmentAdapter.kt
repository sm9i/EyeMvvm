package com.sm9i.eyemvvm.ui.splash.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sm9i.eyemvvm.ui.splash.SloganFragment


const val DEFAULT_SPLASH_VIDEO_COUNT = 4

/**
 * 闪屏页adapter
 */
class SplashVideoFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(
    fragmentManager,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {

    private val mFragmentList = List(DEFAULT_SPLASH_VIDEO_COUNT) {} as MutableList<SloganFragment>
    override fun getItem(position: Int) = mFragmentList[position]

    override fun getCount() = mFragmentList.size

}