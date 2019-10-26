package com.github.khaledakassem.photo_weather.ui.fragments.about_us

import android.os.Bundle
import com.github.khaledakassem.photo_weather.R
import com.github.khaledakassem.photo_weather.databinding.FragmentAboutUsBinding
import com.github.khaledakassem.photo_weather.ui.base.BaseFragment

class AboutUsFragment :AboutUsView, BaseFragment<AboutUsViewModel, FragmentAboutUsBinding>(AboutUsViewModel::class.java) {

    override fun getLayoutRes()= R.layout.fragment_about_us

    override fun initViewModel(viewModel: AboutUsViewModel) {
        mBinding.viewModel=viewModel
    }

    override fun init(savedInstanceState: Bundle?) {

    }

}