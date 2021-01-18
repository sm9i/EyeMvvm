package com.sm9i.eyemvvm.widget.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.viewpager.widget.ViewPager
import kotlin.math.abs


/**
 * 拦截的viewPager
 * 拦截向上的
 * 拦截向左滑动的
 */
class InterceptVerticalViewPager : ViewPager {


    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    private var mLastMotionX: Float = 0f
    private var mLastMotionY: Float = 0f

    lateinit var verticalListener: () -> Unit
    lateinit var horizontalListener: (Int) -> Unit
    private var isDoListener: Boolean = false

    var mDisMissIndex = -1

    //抖动/手的滑动得大于这距离
    private val scaleTouchSlop = ViewConfiguration.get(context).scaledTouchSlop


    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {

        val action = ev.action and MotionEvent.ACTION_MASK
        val y = ev.y
        val x = ev.x
        when (action) {
            MotionEvent.ACTION_DOWN -> {
                //按下的时候记录位置
                mLastMotionX = ev.x
                mLastMotionY = ev.y
            }
            MotionEvent.ACTION_MOVE -> {
                ///记录横纵的具体偏移
                val dx = mLastMotionX - x
                val dy = mLastMotionY - x
                val absX = abs(dx)
                val absY = abs(dy)
                // dy > 0  表示向上滑动
                // absY > absX * 0.5 表示 上下滑动的距离大于x的一半
                if (dy > 0 && absY > absX * 0.5f && absY > scaleTouchSlop) {
                    if (!isDoListener) {
                        verticalListener()
                        isDoListener = true
                        return false
                    }
                }
                if (dx > 0 && absX > absY * 0.5f && absX > scaleTouchSlop) {
                    if (!isDoListener && mDisMissIndex == currentItem) {
                        horizontalListener(mDisMissIndex)
                        isDoListener = true
                        return false

                    }
                }
                mLastMotionY = y
                mLastMotionX = x
            }
        }

        return super.dispatchTouchEvent(ev)
    }

}