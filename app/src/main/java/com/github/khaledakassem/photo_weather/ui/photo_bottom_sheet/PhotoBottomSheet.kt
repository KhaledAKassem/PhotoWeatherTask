package com.github.khaledakassem.photo_weather.ui.photo_bottom_sheet

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.github.khaledakassem.photo_weather.R
import com.github.khaledakassem.photo_weather.common.Constants
import com.github.khaledakassem.photo_weather.common.utils.ImageManger
import com.github.khaledakassem.photo_weather.databinding.BottomSheetChoosePicBinding
import com.github.khaledakassem.photo_weather.ui.base.BaseBottomSheetDialog
import com.github.khaledakassem.photo_weather.ui.fragments.home.HomeViewModel


class PhotoBottomSheet : PhotoBottomSheetView, BaseBottomSheetDialog<HomeViewModel, BottomSheetChoosePicBinding>(HomeViewModel::class.java) {

    override fun getLayoutRes()= R.layout.bottom_sheet_choose_pic

    override fun init(savedInstanceState: Bundle?) {

        mBinding.containerGallery.setOnClickListener {
            checkStoragePermission()
        }
        mBinding.containerCamera.setOnClickListener {
            checkCameraPermission()
        }
    }

    override fun initViewModel(viewModel: HomeViewModel) {
            mBinding.viewModel=viewModel
    }

    override fun checkStoragePermission() {

        if (ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                activity?.requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.REQUEST_STORAGE_PERMISSION_CODE
                )

        } else
            openGallery()

        dismissAllowingStateLoss()
    }

    override fun checkCameraPermission() {

        if (ContextCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                activity?.requestPermissions(
                    arrayOf(android.Manifest.permission.CAMERA),
                    Constants.REQUEST_CAMERA_PERMISSION_CODE
                )
        } else
            openCamera()

        dismissAllowingStateLoss()
    }

    override fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        activity?.startActivityForResult(intent, Constants.GALLERY_REQUEST_CODE)
    }

    override fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageManger.save_image_in_provider(context))
        activity?.startActivityForResult(intent, Constants.CAMERA_REQUEST_CODE)
    }

}