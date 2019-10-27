package com.github.khaledakassem.photo_weather.ui.binding_adapter

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.databinding.BindingAdapter


@BindingAdapter(value = ["imageUri"])
fun setImageUri(imageView: ImageView, imageUri: Bitmap?) {

 imageView.setImageBitmap(imageUri)
}