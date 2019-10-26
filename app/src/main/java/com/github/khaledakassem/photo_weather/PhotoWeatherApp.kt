package com.github.khaledakassem.photo_weather

import android.app.Application
import com.github.khaledakassem.photo_weather.di.component.DaggerAppComponent
import com.github.khaledakassem.photo_weather.di.module.AppModule

class PhotoWeatherApp : Application() {

    val component by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}