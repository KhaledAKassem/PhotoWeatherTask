package com.github.khaledakassem.photo_weather.ui.fragments.photo_history

import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos
import com.github.khaledakassem.photo_weather.ui.base.BaseView

interface PhotoHistoryView :BaseView {
    /**
     * getAllPhotos () used for init and get List of stored photos for users
     */
    fun getAllPhotos()

    /**
     * initPhotosCycle() used for init recycleView with list of images
     * @param list List<Photos>?
     */
    fun initPhotosCycle(list: List<Photos>?)

    /**
     * serPhotoClick (-,-) used for getting id for clicked photo
     * @param id Int
     * @param imageUrl String?
     */
    fun setOnPhotoClick(id: Int, imageUrl: String?)
}