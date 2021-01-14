package com.sm9i.eyemvvm.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.sm9i.base.utils.showKeyboard
import com.sm9i.eyemvvm.R
import com.sm9i.eyemvvm.widget.font.CustomFontTextView
import com.sm9i.eyemvvm.widget.font.FontType


abstract class BaseDataBindActivity<T : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var mDataBinding: T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getBundleExtras(intent.extras)
        if (getContentViewLayoutId() != 0) {
            mDataBinding = DataBindingUtil.setContentView(this, getContentViewLayoutId())
            mDataBinding.lifecycleOwner = this
        } else
            throw IllegalArgumentException("You must set layout")
    }


    /**
     * 初始工具栏 默认加粗
     */
    protected fun initToolBar(
        toolbar: View,
        title: String? = null,
        fontType: FontType = FontType.BOLD
    ) {
//        val ivBack = toolbar.findViewById<ImageView>(R.id.iv_back)
//        ivBack.setOnClickListener {
//            showKeyboard(false)
//            finish()
//        }
//        val tvTitle = toolbar.findViewById<CustomFontTextView>(R.id.tv_title)
//        tvTitle.setFontType(fontType)
//        tvTitle.text = title
    }

    /**
     * 初始化工具栏 resId
     */
    protected fun initToolBar(
        toolbar: View,
        @StringRes id: Int? = null,
        fontType: FontType = FontType.BOLD
    ) {
//        val ivBack = toolbar.findViewById<ImageView>(R.id.iv_back)
//        ivBack.setOnClickListener {
//            showKeyboard(false)
//            finish()
//        }
//        val tvTitle = toolbar.findViewById<CustomFontTextView>(R.id.tv_title)
//        tvTitle.setFontType(fontType)
//        tvTitle.setText(id!!)

    }

    enum class TransitionMode {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM,
        SCALE,
        FADE,
    }

    /**
     * 进场动画
     */
    private fun overrideTransitionAnimation() {
        if (toggleOverridePendingTransition()) {
            when (getOverridePendingTransition()) {
                TransitionMode.TOP -> overridePendingTransition(R.anim.top_in, R.anim.no_anim)
                TransitionMode.BOTTOM -> overridePendingTransition(R.anim.bottom_in, R.anim.no_anim)
                TransitionMode.LEFT -> overridePendingTransition(R.anim.left_in, R.anim.no_anim)
                TransitionMode.RIGHT -> overridePendingTransition(R.anim.right_in, R.anim.no_anim)
                TransitionMode.FADE -> overridePendingTransition(R.anim.fade_in, R.anim.no_anim)
                TransitionMode.SCALE -> overridePendingTransition(R.anim.scale_in, R.anim.no_anim)
            }
        }
    }

    override fun finish() {
        super.finish()
        if (toggleOverridePendingTransition()) {
            when (getOverridePendingTransition()) {
                TransitionMode.TOP -> overridePendingTransition(0, R.anim.top_out)
                TransitionMode.BOTTOM -> overridePendingTransition(0, R.anim.bottom_out)
                TransitionMode.LEFT -> overridePendingTransition(0, R.anim.left_out)
                TransitionMode.RIGHT -> overridePendingTransition(0, R.anim.right_out)
                TransitionMode.FADE -> overridePendingTransition(0, R.anim.fade_out)
                TransitionMode.SCALE -> overridePendingTransition(0, R.anim.scale_out)
            }
        }
    }

    /**
     * 切换动画
     */
    protected open fun toggleOverridePendingTransition() = false


    protected open fun getOverridePendingTransition(): TransitionMode? = null

    /**
     * 初始化数据
     */
    abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 读取bundle的数据
     */
    open fun getBundleExtras(extras: Bundle?) {}

    /**
     *布局id
     */
    abstract fun getContentViewLayoutId(): Int

}