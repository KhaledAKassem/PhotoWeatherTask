package com.github.khaledakassem.photo_weather.data.database.schema

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos

@Database(entities = [Photos::class], version = 1, exportSchema = false)
abstract class PhotosDatabase : RoomDatabase() {

    abstract fun photosDao(): PhotosDao

}