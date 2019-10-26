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
import android.content.ContextWrapper
import android.content.Context
import android.graphics.*
import android.location.Location
import android.os.AsyncTask
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import com.github.khaledakassem.photo_weather.R
import com.github.khaledakassem.photo_weather.common.GlideApp
import com.github.khaledakassem.photo_weather.common.extensions.confirmMsg
import com.github.khaledakassem.photo_weather.common.extensions.loadImg
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class HomeFragment :HomeView, BaseFragment<HomeViewModel, FragmentHomeBinding>(HomeViewModel::class.java) {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    override fun getLayoutRes() = R.layout.fragment_home

    override fun initViewModel(viewModel: HomeViewModel) {
        mBinding.viewModel = viewModel
    }

    override fun init(savedInstanceState: Bundle?) {
        setUpMap()
        initOpenChangePhoto()
    }

    override fun initGetWeatherData() {
        viewModel.getWeather(
            viewModel.latLng!!.latitude.toString(),
            viewModel.latLng!!.longitude.toString()
        ).observe(this, Observer {
            if (it.isResponseSuccessful) {
                mBinding.btnPicPhoto.text = it.responseBody!!.weather[0].description
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

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == Constants.REQUEST_CAMERA_PERMISSION_CODE)
                openCamera()
            else if (requestCode == Constants.REQUEST_STORAGE_PERMISSION_CODE)
                openGallery()
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
                    val source =ImageDecoder.createSource(activity?.contentResolver!!, selectedImage)
                    val  bitmap = ImageDecoder.decodeBitmap(source)

                    mBinding.imgCanvas.loadImg(selectedImage,R.drawable.splash_icon)
                 
                    viewModel.saveInInternal(bitmap)

                    viewModel.photoPath.observe(this, Observer {
                        if(it !=null){
                            viewModel.insertPhoto(it)
                            confirmMsg("Photo Saved Successfully")
                        }
                    })
                }
            }
            Constants.CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    var bitmap =
                        BitmapFactory.decodeFile(ImageManger.get_saved_image(activity!!).absolutePath)
                    bitmap=bitmap.copy(Bitmap.Config.ARGB_8888,true)
                    val canvas = Canvas(bitmap)
                    val paint=Paint(Paint.ANTI_ALIAS_FLAG)
                    paint.color = Color.rgb(61,61,61)
                    paint.textSize=200f
                    val bounds=Rect()
                    val text="Khaled Image"
                    paint.getTextBounds(text,0,text.length,bounds)
                    val x =(bitmap.width -bounds.width())/2
                    val y=(bitmap.height+bounds.height())/4
                    canvas.drawText(text,x.toFloat(),y.toFloat(),paint)
                    img_canvas.setImageBitmap(bitmap)
                    viewModel.saveInInternal(bitmap)

                    viewModel.photoPath.observe(this, Observer {
                        if(it !=null){
                            viewModel.insertPhoto(it)
                            confirmMsg("Photo Saved Successfully")
                        }
                    })
                }
            }
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
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                lastLocation = location
                viewModel.latLng = LatLng(location.latitude, location.longitude)
                initGetWeatherData()
            } else {
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        lastLocation = location
                        viewModel.latLng = LatLng(location.latitude, location.longitude)
                        initGetWeatherData()
                    }
                }
            }
        }
    }
}
class doAsync(val handler: () -> Unit) : AsyncTask<Void, Void, Void>() {
    init {
        execute()
    }

    override fun doInBackground(vararg params: Void?): Void? {
        handler()
        return null
    }


}
