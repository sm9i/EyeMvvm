package com.sm9i.eyemvvm.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.sm9i.eyemvvm.R
import com.sm9i.eyemvvm.base.data.UserSettingLocalDataSource
import com.sm9i.eyemvvm.databinding.ActivityLandingBinding
import com.sm9i.eyemvvm.ui.base.BaseDataBindActivity

class LandingActivity : BaseDataBindActivity<ActivityLandingBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
        if (UserSettingLocalDataSource.isFirstLogin) {
            val action =
                NavigationEmptyFragmentDirections.actionNavigationEmptyFragmentToVideoLandingFragment()
            findNavController(R.id.nav_host).navigate(action)
        } else {
            val action =
                NavigationEmptyFragmentDirections.actionNavigationEmptyFragmentToSloganFragment()
            findNavController(R.id.nav_host).navigate(action)

        }

    }

    override fun getContentViewLayoutId() = R.layout.activity_landing


}