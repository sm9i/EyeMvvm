package com.sm9i.eyemvvm.ui.splash

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.ViewPager
import com.sm9i.eyemvvm.R
import com.sm9i.eyemvvm.base.data.UserSettingLocalDataSource
import com.sm9i.eyemvvm.databinding.FragmentVideoLandingBinding
import com.sm9i.eyemvvm.player.render.IRenderView
import com.sm9i.eyemvvm.ui.base.BaseDataBindActivity
import com.sm9i.eyemvvm.ui.base.BaseDataBindFragment
import com.sm9i.eyemvvm.ui.splash.adapter.DEFAULT_SPLASH_VIDEO_COUNT
import com.sm9i.eyemvvm.ui.splash.adapter.SplashVideoFragmentAdapter


/**
 * 视频闪屏页面
 */
class VideoLandingFragment : BaseDataBindFragment<FragmentVideoLandingBinding>() {

    private var mVideoPosition = 0  //当前播放位置
    private var isHasPaused = false //是否停止


    override fun initViewCreated(saveInstanceState: Bundle?) {
        initSloganText()
    }


    /**
     * 初始化标语
     */
    private fun initSloganText() {
        mDataBinding.apply {
            tvSloganEn.printText(resources.getStringArray(R.array.slogan_array_en)[0])
            tvSloganZh.printText(resources.getStringArray(R.array.slogan_array_zh)[0])
        }
    }

    private fun initSloganFragments() {
        mDataBinding.apply {
            pageIndicator.count = DEFAULT_SPLASH_VIDEO_COUNT
            with(interceptVerticalViewPager) {

                verticalListener = { goMainActivityThenFinish() }
                horizontalListener = { goMainActivityThenFinish() }
                mDisMissIndex = DEFAULT_SPLASH_VIDEO_COUNT - 1

                adapter = SplashVideoFragmentAdapter(childFragmentManager)

                addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
                    override fun onPageScrollStateChanged(state: Int) {

                    }

                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {
                        pageIndicator.setSelected(position)
                    }

                    override fun onPageSelected(position: Int) {
                        if (position in 0..3) {
                            tvSloganEn.printText(resources.getStringArray(R.array.slogan_array_en)[position])
                            tvSloganZh.printText(resources.getStringArray(R.array.slogan_array_zh)[position])
                        }
                    }

                })

            }
        }
    }

    private fun setVideoObserver() {
        lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onVideoResume() {
                if (isHasPaused) {
                    mDataBinding.fullScreenVideoView.seekTo(mVideoPosition)
                    mDataBinding.fullScreenVideoView.resume()
                    isHasPaused = false
                }
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
            fun onVideoPause() {
                mVideoPosition = mDataBinding.fullScreenVideoView.currentPosition
                mDataBinding.fullScreenVideoView.pause()
                isHasPaused = true
            }

            @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
            fun onVideoStop() {
                mDataBinding.fullScreenVideoView.stopPlayback()
            }
        })
    }

    private fun playVideo() {
        val path = R.raw.landing
        with(mDataBinding.fullScreenVideoView) {
            setAspectRatio(IRenderView.AR_ASPECT_FIT_PARENT)
            setVideoPath("android.resource://${activity?.packageName}/$path")
            //无限循环
            setOnPreparedListener {
                requestFocus()
                seekTo(0)
                start()
            }
            setOnCompletionListener {
                it.isLooping = true
                start()
            }
        }
    }

    private fun goMainActivityThenFinish() {
        UserSettingLocalDataSource.isFirstLogin = false
        findNavController().navigate(VideoLandingFragmentDirections.actionVideoLandingFragmentToMainActivity())
        requireActivity().finish()
    }


    override fun getContentViewLayoutId() = R.layout.fragment_video_landing

}