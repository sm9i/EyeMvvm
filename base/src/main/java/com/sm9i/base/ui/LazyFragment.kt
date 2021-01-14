package com.sm9i.base.ui

import androidx.fragment.app.Fragment


/**
 * 懒加载的fragment 配合 [FragmentLazyPagerAdapter]
 */
open class LazyFragment : Fragment() {

    private var isLoaded = false
    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            lazyInit()
            isLoaded = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isLoaded = false
    }

    open fun lazyInit() {}
}