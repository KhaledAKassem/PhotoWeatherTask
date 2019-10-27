package com.github.khaledakassem.photo_weather.ui.fragments.home

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.github.khaledakassem.photo_weather.common.Constants
import com.github.khaledakassem.photo_weather.common.utils.ImageManger
import com.github.khaledakassem.photo_weather.databinding.FragmentHomeBinding
import com.github.khaledakassem.photo_weather.ui.base.BaseFragment
import android.graphics.*
import android.graphics.drawable.Drawable
import androidx.core.app.ActivityCompat
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.SharePhoto
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.widget.ShareDialog
import com.github.khaledakassem.photo_weather.R
import com.github.khaledakassem.photo_weather.common.extensions.confirmMsg
import com.github.khaledakassem.photo_weather.common.extensions.errorMsg
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.io.File
import java.lang.Exception


class HomeFragment : HomeView,
    BaseFragment<HomeViewModel, FragmentHomeBinding>(HomeViewModel::class.java) {

    private lateinit var callbackManager: CallbackManager
    var shareDialog: ShareDialog? = null
    private val target = object : Target {
        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            errorMsg(e.toString())
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            confirmMsg(getString(R.string.image_prepared))
        }

        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {

            val sharePhoto = SharePhoto.Builder()
                .setBitmap(bitmap)
                .setCaption(getString(R.string.photo_shot))
                .build()

            if (ShareDialog.canShow(SharePhotoContent::class.java)) {
                val content = SharePhotoContent.Builder()
                    .addPhoto(sharePhoto)
                    .build()
                shareDialog!!.show(content)
            }
        }

    }

    override fun getLayoutRes() = R.layout.fragment_home

    override fun initViewModel(viewModel: HomeViewModel) {
        mBinding.viewModel = viewModel
    }

    override fun init(savedInstanceState: Bundle?) {
        setUpMap()
        initOpenChangePhoto()
        initSocialMedia()
        shareDialog = ShareDialog(this)
        callbackManager = CallbackManager.Factory.create()
        viewModel.isPhotoSaved.observe(this, Observer {
            if (it) {
                viewModel.isPhotoSaved.value = false
                confirmMsg(getString(R.string.photo_saved))
            }
        })
    }

    override fun initOpenChangePhoto() {
        mBinding.btnPicPhoto.setOnClickListener {
            findNavController().navigate(R.id.photoBottomSheet)
        }
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isEmpty())
            return

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            when (requestCode) {
                Constants.REQUEST_CAMERA_PERMISSION_CODE -> openCamera()
                Constants.REQUEST_STORAGE_PERMISSION_CODE -> openGallery()
                Constants.REQUEST_LOCATION_PERMISSION_CODE -> viewModel.checkLocationStatus(activity!!)
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            Constants.GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val selectedImage: Uri = data?.data!!
                    val cursor = activity?.contentResolver?.query(
                        selectedImage, null,
                        null, null, null
                    )
                    cursor?.close()
                    val bitmap = MediaStore.Images.Media.getBitmap(
                        activity?.contentResolver,
                        selectedImage
                    )
                    initPicWithCanvas(bitmap)
                }
            }

            Constants.CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    try {
                        val bitmap =
                            BitmapFactory.decodeFile(ImageManger.get_saved_image(activity!!).absolutePath)
                        initPicWithCanvas(bitmap)
                    } catch (e: Exception) {
                        errorMsg(getString(R.string.some_thing_lsot))
                    }
                }
            }
            Constants.LOCATION_REQUEST_CODE -> viewModel.getCurrentLocation(context!!)
        }
    }


    override fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                context!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                Constants.REQUEST_LOCATION_PERMISSION_CODE
            )
            return
        }
        viewModel.checkLocationStatus(activity!!)
    }


    override fun initPicWithCanvas(bitmap: Bitmap): Bitmap {

        val bitmapCopy = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        viewModel.weatherData.observe(this, Observer {
            val canvas = Canvas(bitmapCopy)


            val paint = Paint(Paint.ANTI_ALIAS_FLAG)
            paint.typeface = Typeface.create("Arial", Typeface.BOLD)
            paint.color = Color.YELLOW
            paint.textSize = resources.getDimension(R.dimen.x50sp)

            val bounds = Rect()
            val weatherInfo =
                "${it.sys.country} , ${it.weather[0].main} , ${it.weather[0].description}"
            paint.getTextBounds(weatherInfo, 0, weatherInfo.length, bounds)
            val x = (bitmapCopy.width - bounds.width()) / 2
            val y = (bitmapCopy.height + bounds.height()) / 14
            canvas.drawText(weatherInfo, x.toFloat(), y.toFloat(), paint)


            viewModel.selectedImage.postValue(bitmapCopy)
            viewModel.saveInInternal(bitmapCopy)

        })
        return bitmapCopy
    }


    override fun initSocialMedia() {

        mBinding.fbShare.setOnClickListener {
            val path = viewModel.imageUri.value
            if (!path.isNullOrEmpty()) {
                Picasso.get().load(File(path!!)).into(target)
                shareDialog!!.registerCallback(
                    callbackManager,
                    object : FacebookCallback<Sharer.Result> {
                        override fun onSuccess(result: Sharer.Result?) {
                            confirmMsg(getString(R.string.shared_successfully))
                        }

                        override fun onCancel() {
                            errorMsg(getString(R.string.operation_cancelled))
                        }

                        override fun onError(error: FacebookException?) {
                            errorMsg(getString(R.string.something_error))
                        }
                    })
            } else {
                errorMsg(getString(R.string.pic_first))
            }
        }
    }
}

