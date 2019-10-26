package com.github.khaledakassem.photo_weather.common.extensions

import android.app.Activity
import android.content.Intent
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import com.github.khaledakassem.photo_weather.PhotoWeatherApp



fun Activity.getThisColor(@ColorRes id: Int) = ContextCompat.getColor(baseContext, id)

fun Activity.goToActivity(activityClass: Class<*>) = this.startActivity(Intent(this, activityClass))

fun Activity.appComponent() = (this.application as PhotoWeatherApp).component



