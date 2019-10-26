package com.github.khaledakassem.photo_weather.data.network

import androidx.lifecycle.MutableLiveData
import com.github.khaledakassem.photo_weather.data.network.entities.ApiResponse
import com.github.khaledakassem.photo_weather.data.network.entities.WeatherInfo

interface NetworkHelper {

    fun getWeather(lat:String,lon:String,appId:String=""): MutableLiveData<ApiResponse<WeatherInfo>>
}