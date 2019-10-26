package com.github.khaledakassem.photo_weather.di.component

import android.content.Context
import android.content.SharedPreferences
import com.github.khaledakassem.photo_weather.di.module.AppModule
import com.github.khaledakassem.photo_weather.di.module.NetworkModule
import com.github.khaledakassem.photo_weather.PhotoWeatherApp
import com.github.khaledakassem.photo_weather.di.module.DatabaseModule
import com.github.khaledakassem.photo_weather.ui.base.BaseViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun app(): PhotoWeatherApp
    fun context(): Context

    fun inject(baseViewModel: BaseViewModel)

    fun getSharedPreference(): SharedPreferences
}