package com.sm9i.eyemvvm.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sm9i.base.ui.LazyFragment


/**
 * fragment base
 */
abstract class BaseDataBindFragment<T : ViewDataBinding> : LazyFragment() {

    protected lateinit var mDataBinding: T


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleExtras(arguments)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (getContentViewLayoutId() != 0) {
            mDataBinding =
                DataBindingUtil.inflate(inflater, getContentViewLayoutId(), container, false)
            mDataBinding.lifecycleOwner = viewLifecycleOwner
            return mDataBinding.root
        } else throw IllegalArgumentException("You must set layou id")

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewCreated(savedInstanceState)
    }


    /**
     * 获取bundle中的data
     */
    open fun getBundleExtras(extras: Bundle?) {}

    /**
     * 获取资源id
     */
    abstract fun getContentViewLayoutId(): Int

    /**
     * 初始化方法 懒加载 lazyInit
     */
    abstract fun initViewCreated(saveInstanceState: Bundle?)
}