package com.github.khaledakassem.photo_weather.ui.fragments.photo_history

import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos
import com.github.khaledakassem.photo_weather.ui.base.BaseView

interface PhotoHistoryView :BaseView {
    fun getAllPhotos()
    fun initPhotosCycle(list: List<Photos>?)
    fun setOnPhotoClick(id: Int, imageUrl: String?)
}