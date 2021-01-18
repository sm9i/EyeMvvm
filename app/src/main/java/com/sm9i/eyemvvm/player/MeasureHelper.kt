package com.sm9i.eyemvvm.player

import android.view.View
import com.sm9i.eyemvvm.player.render.IRenderView
import io.reactivex.internal.operators.flowable.FlowableElementAt
import io.reactivex.internal.operators.flowable.FlowableTakeLastOne
import java.lang.ref.WeakReference


/**
 * 测量帮助类
 */
class MeasureHelper(view: View) {

    private val mWeakView: WeakReference<View>?
    private var mVideoWidth = 0     //布局宽度
    private var mVideoHeight = 0    //布局高度
    private var mVideoSarNum = 0    //实际视频宽

    private var mVideoSarDen = 0    //实际视频高
    private var mVideoRotationDegree = 0    //屏幕旋转度数


    var measureWidth = 0 //测量后的宽度
    var measureHeight = 0 //测量后的高度

    private var mCurrentAspectRatio = IRenderView.AR_ASPECT_FIT_PARENT  //宽高比例

    val view: View? get() = mWeakView?.get()

    init {
        mWeakView = WeakReference(view)
    }

    fun setVideoSize(videoWidth: Int, videoHeight: Int) {
        mVideoWidth = videoWidth
        mVideoHeight = videoHeight
    }

    fun setVideoSampleAspectRatio(videoSarNum: Int, videoSarDen: Int) {
        mVideoSarNum = videoSarNum
        mVideoSarDen = videoSarDen
    }

    fun setVideoRotation(videoRotationDegree: Int) {
        mVideoRotationDegree = videoRotationDegree
    }


    fun doMeasure(widthMeasureSpce: Int, heightMeasureSpec: Int) {
        var widthMeasureSpec = widthMeasureSpce
        var heightMeasureSpec = heightMeasureSpec

        if (mVideoRotationDegree == 90 || mVideoRotationDegree == 270) {
            //如果为横屏、宽高对调
            val tempSpec = widthMeasureSpec
            widthMeasureSpec = heightMeasureSpec
            heightMeasureSpec = tempSpec
        }

        //获取父布局宽高
        var width = View.getDefaultSize(mVideoWidth, widthMeasureSpec)
        var height = View.getDefaultSize(mVideoHeight, heightMeasureSpec)

        //如果宽高比例是匹配父布局 则不变
        if (mCurrentAspectRatio == IRenderView.AR_MATCH_PARENT) {
            width = widthMeasureSpce
            height = heightMeasureSpec
        } else if (mVideoWidth > 0 && mVideoHeight > 0) {
            //设置的视频宽高大于0 时

            val widthSpecMode = View.MeasureSpec.getMode(widthMeasureSpec)
            val widthSpecSize = View.MeasureSpec.getSize(widthMeasureSpec)
            val heightSpecMode = View.MeasureSpec.getMode(heightMeasureSpec)
            val heightSpecSize = View.MeasureSpec.getSize(heightMeasureSpec)
            //当前控件测量模式为包裹模式时，根据视频比例与渲染比例 计算视频显示宽高比
            if (widthSpecMode == View.MeasureSpec.AT_MOST && heightSpecMode == View.MeasureSpec.AT_MOST) {
                val specAspectRatio = widthSpecSize.toFloat() / heightSpecSize.toFloat()
                var displayAspectRatio: Float
                //
                when (mCurrentAspectRatio) {
                    IRenderView.AR_16_9_FIT_PARENT -> {
                        displayAspectRatio = 16.0f / 9.0f
                        if (mVideoRotationDegree == 90 || mVideoRotationDegree == 270) {
                            displayAspectRatio = 1.0f / displayAspectRatio
                        }
                    }
                    IRenderView.AR_4_3_FIT_PARENT -> {
                        displayAspectRatio = 4.0f / 3.0f
                        if (mVideoRotationDegree == 90 || mVideoRotationDegree == 270) {
                            displayAspectRatio = 1.0f / displayAspectRatio
                        }
                    }
                    IRenderView.AR_ASPECT_FIT_PARENT,
                    IRenderView.AR_ASPECT_FILL_PARENT,
                    IRenderView.AR_ASPECT_WRAP_CONTENT -> {
                        displayAspectRatio = mVideoWidth.toFloat() / mVideoHeight.toFloat()
                        if (mVideoSarNum > 0 && mVideoSarDen > 0) {
                            displayAspectRatio = displayAspectRatio * mVideoSarNum / mVideoSarDen
                        }
                    }
                    else -> {
                        displayAspectRatio = mVideoWidth.toFloat() / mVideoHeight.toFloat()
                        if (mVideoSarNum > 0 && mVideoSarDen > 0) {
                            displayAspectRatio = displayAspectRatio * mVideoSarNum / mVideoSarDen
                        }
                    }
                }

                // 高度偏小   false 宽度偏小
                val shouldBeWider = displayAspectRatio > specAspectRatio

                when (mCurrentAspectRatio) {
                    IRenderView.AR_ASPECT_FIT_PARENT,
                    IRenderView.AR_16_9_FIT_PARENT,
                    IRenderView.AR_4_3_FIT_PARENT -> {
                        if (shouldBeWider) {
                            width = widthSpecSize
                            height = (width / displayAspectRatio).toInt()
                        } else {
                            height = heightSpecSize
                            width = (height * displayAspectRatio).toInt()
                        }
                    }
                    //填充  小边固定 大的缩放
                    IRenderView.AR_ASPECT_FILL_PARENT -> {
                        if (shouldBeWider) {
                            height = heightSpecSize
                            width = (height * displayAspectRatio).toInt()
                        } else {
                            width = widthSpecSize
                            height = (width / displayAspectRatio).toInt()

                        }
                    }
                    //包裹 以视频采样的宽高为准
                    IRenderView.AR_ASPECT_WRAP_CONTENT -> {
                        if (shouldBeWider) {
                            //width = if (mVideoWidth > widthSpecMode) widthSpecMode else mVideoWidth
                            width = mVideoWidth.coerceAtMost(widthSpecMode)
                            height = (width / displayAspectRatio).toInt()
                        } else {
                            height = mVideoHeight.coerceAtMost(heightSpecSize)
                            width = (height * displayAspectRatio).toInt()
                        }
                    }
                    else -> {
                        if (shouldBeWider) {
                            width = mVideoWidth.coerceAtMost(widthSpecSize)
                            height = (width / displayAspectRatio).toInt()
                        } else {
                            height = mVideoHeight.coerceAtMost(heightSpecSize)
                            width = (height * displayAspectRatio).toInt()
                        }
                    }
                }

            }
            ///视频宽高为准确模式下，小的固定 大的缩放
            else if (widthSpecMode == View.MeasureSpec.EXACTLY && heightSpecMode == View.MeasureSpec.EXACTLY) {
                width = widthSpecSize
                height = heightSpecSize
                if (mVideoWidth * height < width * mVideoHeight) {
                    //宽度过大
                    width = height * mVideoWidth / mVideoHeight
                } else if (mVideoWidth * height > width * mVideoHeight) {
                    //高度过大
                    height = width * mVideoHeight / mVideoWidth
                }
            }
            //宽度准确  高度不能超过父布局
            else if (widthSpecMode == View.MeasureSpec.EXACTLY) {
                width = widthMeasureSpce
                height = width * mVideoHeight / mVideoWidth
                if (heightSpecMode == View.MeasureSpec.AT_MOST && height > heightSpecSize) {
                    height = heightSpecSize
                }
            }
            //高度准确 宽度不能超过父布局
            else if (heightSpecMode == View.MeasureSpec.EXACTLY) {
                height = heightSpecSize
                width = height * mVideoWidth / mVideoHeight
                if (widthSpecMode == View.MeasureSpec.AT_MOST && width > widthSpecSize) {
                    width = widthSpecSize
                }
            }
            //宽高都不固定、使用实际宽高
            else {
                width = mVideoWidth
                height = mVideoHeight
                if (heightSpecMode == View.MeasureSpec.AT_MOST && height > heightSpecSize) {
                    //太高
                    height = heightSpecSize
                    width = height * mVideoWidth / mVideoHeight
                }
                if (widthSpecMode == View.MeasureSpec.AT_MOST && width > widthSpecSize) {
                    width = widthSpecSize
                    height = width * mVideoHeight / mVideoWidth
                }
            }
        } else {

        }
        measureWidth = width
        measureHeight = height
    }

    fun setAspectRatio(aspectRatio: Int) {
        mCurrentAspectRatio = aspectRatio
    }

}