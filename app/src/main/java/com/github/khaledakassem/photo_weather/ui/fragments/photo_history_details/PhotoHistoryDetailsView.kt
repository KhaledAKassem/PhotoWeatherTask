package com.github.khaledakassem.photo_weather.ui.fragments.photo_history_details

import com.github.khaledakassem.photo_weather.ui.base.BaseView

interface PhotoHistoryDetailsView :BaseView {

    /**
     * init full image view
     */
    fun initViews()
    /**
     * Share Image on Facebook using FB SDK for sharing image
     * using Picasso with target class
     */
    fun initSocialMedia()

}