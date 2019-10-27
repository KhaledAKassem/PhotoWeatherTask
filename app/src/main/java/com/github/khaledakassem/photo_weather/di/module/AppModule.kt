package com.github.khaledakassem.photo_weather.di.module

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.paging.PagedList
import com.github.khaledakassem.photo_weather.PhotoWeatherApp
import com.github.khaledakassem.photo_weather.common.Constants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Module which provides all required dependencies for tha entire app
 */
@Module
class AppModule(val application: PhotoWeatherApp) {

    /**
     * Provides App instance of the application
     *
     * @return the instance of application
     */
    @Provides
    @Singleton
    fun provideApplication() = application

    /**
     * provide context to be used in data repository
     *
     * @return applicationContext
     */
    @Provides
    @Singleton
    fun provideContext(): Context = application.applicationContext

    /**
     * provide shared preference to store data
     *
     * @return shared preference instance
     */
    @Provides
    @Singleton
    fun provideSharedPref(): SharedPreferences = application.getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE)
    
}