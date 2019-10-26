package com.github.khaledakassem.photo_weather.ui.fragments.photo_history

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos
import com.github.khaledakassem.photo_weather.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PhotoHistoryViewModel (app:Application):BaseViewModel(app) {

    /** liveData that hold the list of fav movies **/
    private val allPhotos = MutableLiveData<List<Photos>> ()

    /**
     * get the stored photos from database using coroutines .
     * @return MutableLiveData<List<Photos>> the liveData that hold the photos
     */
    fun getAllPhotos(): MutableLiveData<List<Photos>> {
        viewModelScope.launch {
            isLoading.postValue(true)
            val photos = withContext(Dispatchers.IO) {
                appRepositoryHelper.getAllPhotos()
            }
            isLoading.postValue(false)
            allPhotos.postValue(photos)
        }
        return allPhotos
    }
}