package com.sm9i.eyemvvm.player

import android.view.View
import com.sm9i.eyemvvm.player.render.IRenderView
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

        }


    }

}