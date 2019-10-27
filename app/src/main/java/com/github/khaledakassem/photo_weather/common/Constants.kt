package com.github.khaledakassem.photo_weather.common

object Constants {

    //numbers
    const val CACHE_SIZE = 10 * 1024 * 1024 //10MB
    const val CACHE_MAX_AGE = 30 //30 seconds
    const val CACHE_MAX_STALE = 60 * 60 * 24 * 7 //1 week

    const val ARABIC = 1
    const val ENGLISH = 0
    const val NOT_DEFINED_LANG = -1

    //time
    const val SNAK_BAR_DURATION: Int = 3 * 1000
    
    //strings
    const val NETWORKING_LOG = "networking"

    //shared preference keys
    const val PREFERENCE_NAME = "MOVIES_SHARED_PREFERENCE"
    const val CURRENT_LANGUAGE_KEY = "CURRENT_LANGUAGE"

    // database name
    const val DATABASE_NAME = "PhotoWeather_app_db"

    // Constants for Permissions
    const val GALLERY_REQUEST_CODE: Int = 98
    const val CAMERA_REQUEST_CODE: Int = 97
    const val REQUEST_CAMERA_PERMISSION_CODE: Int = 96
    const val REQUEST_STORAGE_PERMISSION_CODE: Int = 95
    const val REQUEST_LOCATION_PERMISSION_CODE: Int = 94
    const val LOCATION_REQUEST_CODE: Int = 93
}