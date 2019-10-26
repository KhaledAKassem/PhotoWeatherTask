package com.github.khaledakassem.photo_weather.ui.fragments.home


import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos
import com.github.khaledakassem.photo_weather.data.network.entities.ApiResponse
import com.github.khaledakassem.photo_weather.data.network.entities.WeatherInfo
import com.github.khaledakassem.photo_weather.ui.base.BaseViewModel
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class HomeViewModel (app:Application):BaseViewModel(app) {

    var latLng: LatLng?=null
     val photoPath = MutableLiveData<String> ()

    fun getWeather(lat:String,lon:String): MutableLiveData<ApiResponse<WeatherInfo>> {
        isLoading.value = true
        val apiResponse = appRepositoryHelper.getWeather(lat,lon)
        errorResponse.removeSource(apiResponse)
        errorResponse.addSource(apiResponse) {
            isLoading.value = false
            if (it.responseCode != 401 && it.responseCode != 422)
                errorResponse.value = it
            else if(it.responseCode ==422)
                errorResponse.value=it
        }
        return apiResponse
    }

    /**
     * create instance of photos and insert it into database using coroutines,
     * then update the value of both  isFavMovie liveData and favMovie.
     */
     fun insertPhoto(photoPath:String) {

        viewModelScope.launch {
            isLoading.postValue(true)
            val photo =
                Photos(imageUrl = photoPath)
            withContext(Dispatchers.IO){
                appRepositoryHelper.insertPhoto(photo)
                isLoading.postValue(false)
            }
        }
    }

    fun saveInInternal(bitmapImage: Bitmap) {
        isLoading.postValue(true)
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val path =saveToInternalStorage(bitmapImage)
                photoPath.postValue(path)
            }
        }
    }

    fun  saveToInternalStorage(bitmapImage: Bitmap): String {
        val cw = ContextWrapper(app.applicationContext)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val rnds = (0..1000).random()
        val mypath = File(directory, "$rnds.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(mypath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return mypath.absolutePath
    }



}