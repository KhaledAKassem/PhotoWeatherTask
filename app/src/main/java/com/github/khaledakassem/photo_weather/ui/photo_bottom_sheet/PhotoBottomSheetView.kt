package com.github.khaledakassem.photo_weather.ui.photo_bottom_sheet

import com.github.khaledakassem.photo_weather.ui.base.BaseView


interface PhotoBottomSheetView  : BaseView {

 fun checkCameraPermission()
 fun checkStoragePermission()

}