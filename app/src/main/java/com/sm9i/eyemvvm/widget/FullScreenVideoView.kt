package com.sm9i.eyemvvm.widget

import android.content.Context
import android.util.AttributeSet
import android.widget.VideoView
import com.sm9i.eyemvvm.player.MeasureHelper


/**
 * 全屏VideoView
 */

class FullScreenVideoView : VideoView {

    private var mMeasureHelper: MeasureHelper = MeasureHelper(this)

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mMeasureHelper.doMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mMeasureHelper.measureWidth, mMeasureHelper.measureHeight)
    }

    fun setAspectRatio(aspectRatio: Int) {
        mMeasureHelper.setAspectRatio(aspectRatio)
        requestLayout()
    }

}