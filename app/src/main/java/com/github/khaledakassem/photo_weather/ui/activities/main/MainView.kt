package com.github.khaledakassem.photo_weather.ui.activities.main

import com.github.khaledakassem.photo_weather.ui.base.BaseView

interface MainView :BaseView {

    /**
     * init Bottom Sheet with its fragments and handle cases of clicks
     */
    fun initBottomNavigation()

}