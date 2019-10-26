package com.github.khaledakassem.photo_weather.data

import com.github.khaledakassem.photo_weather.data.database.DatabaseHelper
import com.github.khaledakassem.photo_weather.data.network.NetworkHelper
import com.github.khaledakassem.photo_weather.data.preferences.PreferenceHelper

interface RepositoryHelper : NetworkHelper, PreferenceHelper, DatabaseHelper