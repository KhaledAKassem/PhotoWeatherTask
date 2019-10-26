package com.github.khaledakassem.photo_weather.data.database.schema

import androidx.room.Dao
import androidx.room.Query
import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos

@Dao
interface PhotosDao : BaseDao<Photos> {

    @Query("SELECT * FROM photos")
    suspend fun getAllPhotos(): List<Photos>

}