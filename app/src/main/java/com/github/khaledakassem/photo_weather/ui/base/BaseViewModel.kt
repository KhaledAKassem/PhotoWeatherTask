package com.github.khaledakassem.photo_weather.ui.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.github.khaledakassem.photo_weather.PhotoWeatherApp
import com.github.khaledakassem.photo_weather.data.AppRepositoryHelper
import com.github.khaledakassem.photo_weather.data.network.entities.ApiResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
abstract class BaseViewModel(val app: Application) : AndroidViewModel(app) {

    @Inject
    lateinit var appRepositoryHelper: AppRepositoryHelper

    init {
        (app as? PhotoWeatherApp)?.component?.inject(this)
    }


    /** livData of boolean value used to handle displaying and hiding of progressbar **/
    val isLoading = MutableLiveData<Boolean>().apply { value = false }

    /**
     * MediatorLiveData, all requests will be added as source to it to handle any error or failure.
     **/
    val errorResponse = MediatorLiveData<ApiResponse<*>>()

    /** liveData of boolean value tha will close the current fragment when set to true**/
    val isUpBtnClicked = MutableLiveData<Boolean>().apply { value = false }

}
