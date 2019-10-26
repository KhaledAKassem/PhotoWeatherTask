package com.github.khaledakassem.photo_weather.data.database

import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos

interface DatabaseHelper {

    /**
     * use this method to insert a movie as row in movies table
     * @param Photos Photos the movie to be inserted
     */
    suspend fun insertPhoto(Photos: Photos)

    /**
     * use this method to get all the fav movies stored in the movies table
     * @return List<Photos> the list of fav movies
     */
    suspend fun getAllPhotos() : List<Photos>



}