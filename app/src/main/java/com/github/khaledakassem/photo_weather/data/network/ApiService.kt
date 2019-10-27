package com.github.khaledakassem.photo_weather.data.network

import com.github.khaledakassem.photo_weather.data.network.entities.WeatherInfo
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("weather")
    fun getWeather(@Query("lat") lat : String, @Query("lon") lon : String, @Query("appid") appid : String) : Call<WeatherInfo>

}