package com.github.khaledakassem.photo_weather.data.database.schema.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photos")
 data class Photos (
    @PrimaryKey(autoGenerate  = true)
    var id: Int = 0,
    var imageUrl: String? = null
)