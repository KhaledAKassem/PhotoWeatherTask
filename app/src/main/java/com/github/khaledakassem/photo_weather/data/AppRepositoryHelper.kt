package com.github.khaledakassem.photo_weather.data

import com.github.khaledakassem.photo_weather.BuildConfig
import com.github.khaledakassem.photo_weather.data.database.AppDatabaseHelper
import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos
import com.github.khaledakassem.photo_weather.data.network.AppNetworkHelper
import com.github.khaledakassem.photo_weather.data.preferences.AppPreferenceHelper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryHelper @Inject constructor(
    private val networkHelper: AppNetworkHelper,
    private val preferenceHelper : AppPreferenceHelper,
    private val databaseHelper: AppDatabaseHelper) : RepositoryHelper {

    /** networking **/
    override fun getWeather(
        lat: String, lon: String, appId: String
    ) = networkHelper.getWeather(lat,lon, BuildConfig.API_KEY)

    /** preferences **/
    override fun setUserLanguage(language: Int) = preferenceHelper.setUserLanguage(language)
    override fun getUserLanguage() = preferenceHelper.getUserLanguage()


    /** database **/
    override suspend fun insertPhoto(Photos: Photos) = databaseHelper.insertPhoto(Photos)
    override suspend fun getAllPhotos() = databaseHelper.getAllPhotos()
}