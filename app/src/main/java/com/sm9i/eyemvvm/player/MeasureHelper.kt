package com.sm9i.eyemvvm.player

import android.view.View
import java.lang.ref.WeakReference


class MeasureHelper(view: View) {

    private val mWeakView: WeakReference<View>?
    private var mVideoWidth = 0     //布局宽度
    private var mVideoHeight = 0    //布局高度
    private var mVideoSarNum = 0    //实际视频宽

    private var mVideoSarDen = 0    //实际视频高
    private var mVideoROTATIONDEGREE = 0    //屏幕旋转度数


    var measureWidth = 0 //测量后的宽度
    var measureHeight = 0 //测量后的高度



    init {
        mWeakView = WeakReference(view)
    }

    val view: View? get() = mWeakView?.get()

}