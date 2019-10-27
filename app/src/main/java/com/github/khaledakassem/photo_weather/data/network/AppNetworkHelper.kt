package com.github.khaledakassem.photo_weather.data.network

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import com.github.khaledakassem.photo_weather.common.extensions.getResponse
import com.github.khaledakassem.photo_weather.data.network.entities.ApiResponse
import com.github.khaledakassem.photo_weather.data.network.entities.WeatherInfo

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppNetworkHelper @Inject constructor(
    private val apiService: ApiService,
    private val preferences: SharedPreferences
) : NetworkHelper {

    /**
     * getWeather used for getting result of weather conditions
     * @param lat String
     * @param lon String
     * @param appId String
     * @return MutableLiveData<ApiResponse<WeatherInfo>>
     */
    override fun getWeather(lat:String,lon:String,appId:String): MutableLiveData<ApiResponse<WeatherInfo>> {
        return apiService.getWeather(lat,lon,appId).getResponse()
    }


}