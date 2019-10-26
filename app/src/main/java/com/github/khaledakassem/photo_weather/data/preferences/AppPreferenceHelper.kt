package com.github.khaledakassem.photo_weather.data.preferences

import android.content.SharedPreferences
import com.github.khaledakassem.photo_weather.common.Constants
import com.github.khaledakassem.photo_weather.common.extensions.get
import com.github.khaledakassem.photo_weather.common.extensions.put
import javax.inject.Inject

class AppPreferenceHelper @Inject constructor(private val preferences: SharedPreferences) :
    PreferenceHelper {


    override fun setUserLanguage(language: Int)
            = preferences.put(Constants.CURRENT_LANGUAGE_KEY, language)

    override fun getUserLanguage() = preferences.get(
        Constants.CURRENT_LANGUAGE_KEY, Constants.NOT_DEFINED_LANG
    )


}