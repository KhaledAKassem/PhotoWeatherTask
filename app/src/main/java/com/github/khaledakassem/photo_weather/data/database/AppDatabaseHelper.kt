package com.github.khaledakassem.photo_weather.data.database

import com.github.khaledakassem.photo_weather.data.database.schema.PhotosDatabase
import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos
import javax.inject.Inject

class AppDatabaseHelper @Inject constructor(private val db: PhotosDatabase) : DatabaseHelper {

    override suspend fun insertPhoto(Photos: Photos) =
        db.photosDao().insert(Photos)

    override suspend fun getAllPhotos() = db.photosDao().getAllPhotos()

}