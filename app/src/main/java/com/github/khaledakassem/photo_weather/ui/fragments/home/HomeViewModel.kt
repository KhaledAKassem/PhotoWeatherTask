package com.github.khaledakassem.photo_weather.ui.fragments.home


import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.content.IntentSender
import android.graphics.Bitmap
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.github.khaledakassem.photo_weather.common.Constants
import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos
import com.github.khaledakassem.photo_weather.data.network.entities.WeatherInfo
import com.github.khaledakassem.photo_weather.ui.base.BaseViewModel
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*


class HomeViewModel(app: Application) : BaseViewModel(app) {

    val selectedImage = MutableLiveData<Bitmap>()
    val imageUri = MutableLiveData<String>()

    val weatherData = MutableLiveData<WeatherInfo>()
    var currentLocation: Location? = null


    val isPhotoSaved by lazy {
        MutableLiveData<Boolean>().apply { value = false }
    }

    private val locationRequest = LocationRequest().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 500
    }

    /**
     * getCurrentLocation(-) :used for getting current location for user .
     * @param context Context
     */
    @SuppressLint("MissingPermission")
    fun getCurrentLocation(context: Context) {

        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        val listener = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                fusedLocationProviderClient.removeLocationUpdates(this)
                if (currentLocation == null) {
                    currentLocation = locationResult?.lastLocation
                    getWeather(
                        currentLocation!!.latitude.toString(),
                        currentLocation!!.longitude.toString()
                    )
                }
            }
        }

        fusedLocationProviderClient.requestLocationUpdates(locationRequest, listener, null)
    }


    fun checkLocationStatus(activity: Activity) {
        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)

        builder.setAlwaysShow(true)
        val task = SettingsClient(activity).checkLocationSettings(builder.build())
        task.addOnSuccessListener { getCurrentLocation(activity) }

        task.addOnFailureListener(activity) {
            if (it is ResolvableApiException) {
                try {
                    it.startResolutionForResult(activity, Constants.LOCATION_REQUEST_CODE)
                } catch (sendEx: IntentSender.SendIntentException) {
                }
            }
        }
    }

    /**
     * getWeather used for getting Weather data through lat and long
     * @param lat String
     * @param lon String
     */
    fun getWeather(lat: String, lon: String) {
        isLoading.value = true
        val apiResponse = appRepositoryHelper.getWeather(lat, lon)
        errorResponse.removeSource(apiResponse)
        errorResponse.addSource(apiResponse) {
            isLoading.value = false
            weatherData.postValue(it.responseBody)
            if (it.responseCode != 401 && it.responseCode != 422)
                errorResponse.value = it
            else if (it.responseCode == 422)
                errorResponse.value = it
        }
    }

    /**
     * create instance of photos and insert it into database using coroutines,
     * then update the value of both  isFavMovie liveData and favMovie.
     */
    private suspend fun insertPhoto(photoPath: String) {
        val photo = Photos(imageUrl = photoPath)
        appRepositoryHelper.insertPhoto(photo)

        isPhotoSaved.postValue(true)
        isLoading.postValue(false)
    }

    /**
     * save path for image on room
     * @param bitmapImage Bitmap
     */
    fun saveInInternal(bitmapImage: Bitmap) {
        isLoading.value = true
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val path = saveToInternalStorage(bitmapImage)
                imageUri.postValue(path)
                insertPhoto(path)
            }
        }
    }


    /**
     * save bitmap image which caught from Gallery or Camera on internal storage
     * @param bitmapImage Bitmap
     * @return String
     */
    private fun saveToInternalStorage(bitmapImage: Bitmap): String {
        val cw = ContextWrapper(app.applicationContext)
        val directory = cw.getDir("imageDir", Context.MODE_PRIVATE)
        val photoName = Date().time
        val photoPath = File(directory, "$photoName.jpg")
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(photoPath)
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 50, fos)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                fos!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return photoPath.absolutePath
    }


}