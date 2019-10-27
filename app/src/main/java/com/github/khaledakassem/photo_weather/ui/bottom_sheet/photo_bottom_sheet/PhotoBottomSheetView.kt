package com.github.khaledakassem.photo_weather.ui.bottom_sheet.photo_bottom_sheet

import com.github.khaledakassem.photo_weather.ui.base.BaseView


interface PhotoBottomSheetView  : BaseView {

 /**
  * check Camera permission if it is granted or no ..
  */
 fun checkCameraPermission()

 /**
  * check storage permission if it is Granted or no ..
  */
 fun checkStoragePermission()

}