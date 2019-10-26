package com.github.khaledakassem.photo_weather.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import androidx.room.Room
import com.github.khaledakassem.photo_weather.common.Constants
import com.github.khaledakassem.photo_weather.data.database.schema.PhotosDatabase

/**
 * Module which provides all required dependencies about Room Database
 */
@Module
class DatabaseModule {


    /**
     * Provides the database object
     *
     * @param context the context
     * @return the PhotosDatabase instance
     */
    @Provides
    @Singleton
    fun provideDatabase(context: Context) = Room
        .databaseBuilder(context, PhotosDatabase::class.java, Constants.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build()
}