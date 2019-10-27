package com.github.khaledakassem.photo_weather.ui.fragments.home

import android.graphics.Bitmap
import com.github.khaledakassem.photo_weather.ui.base.BaseView

interface HomeView :BaseView {

    /**
     * initOpenChangePhoto() -- used for navigate to PhotoBottomSheet Fragment
     */
    fun initOpenChangePhoto()

    /**
     * setUpMap() -- used for check permissions for location
     */
    fun setUpMap()

    /**
     * init canvas drawable
     * @param bitmap Bitmap
     * @return Bitmap
     */
    fun initPicWithCanvas(bitmap: Bitmap): Bitmap

    /**
     * init Social media facebook for sharing photo through facebook account
     */
    fun initSocialMedia()

}