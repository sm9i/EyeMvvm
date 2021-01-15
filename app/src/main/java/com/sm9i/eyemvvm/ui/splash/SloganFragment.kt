package com.sm9i.eyemvvm.ui.splash

import android.os.Bundle
import com.sm9i.eyemvvm.R
import com.sm9i.eyemvvm.databinding.FragmentSloganBinding
import com.sm9i.eyemvvm.ui.base.BaseDataBindFragment


/**
 * 空白fragment
 */
class SloganFragment : BaseDataBindFragment<FragmentSloganBinding>() {

    companion object {
        @JvmStatic
        fun newInstance() = SloganFragment()
    }

    override fun initViewCreated(saveInstanceState: Bundle?) {

    }

    override fun getContentViewLayoutId() = R.layout.fragment_slogan

}