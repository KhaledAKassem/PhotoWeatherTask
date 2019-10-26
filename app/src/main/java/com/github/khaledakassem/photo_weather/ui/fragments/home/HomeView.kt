package com.github.khaledakassem.photo_weather.ui.fragments.home

import com.github.khaledakassem.photo_weather.ui.base.BaseView

interface HomeView :BaseView {
    fun initOpenChangePhoto()
    fun setUpMap()
    fun initGetWeatherData()
}