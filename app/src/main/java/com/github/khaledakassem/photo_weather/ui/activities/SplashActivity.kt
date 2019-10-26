package com.github.khaledakassem.photo_weather.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.khaledakassem.photo_weather.common.extensions.goToActivity
import com.github.khaledakassem.photo_weather.ui.activities.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        goToActivity(MainActivity::class.java)
        finish()
    }
}
