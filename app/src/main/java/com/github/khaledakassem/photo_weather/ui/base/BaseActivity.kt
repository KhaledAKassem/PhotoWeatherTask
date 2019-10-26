package com.github.khaledakassem.photo_weather.ui.base

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.facebook.FacebookSdk
import com.github.khaledakassem.photo_weather.common.Constants
import com.github.khaledakassem.photo_weather.common.utils.ImageManger

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>
    (private val mViewModelClass: Class<VM>) : AppCompatActivity(), BaseView {

    val mBinding by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes()) as DB
    }

    val viewModel by lazy {
        ViewModelProviders.of(this).get(mViewModelClass)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        initViewModel(viewModel)
        initLifeCycleOwner()
        observeLiveDatas()
        init(savedInstanceState)
    }

    /**
     *  You need to override this method.
     *  And you need to set viewModel to mBinding: mBinding.viewModel = viewModel
     *
     *  @param viewModel the instance of ViewModel that is related to the  activity
     */
    abstract fun initViewModel(viewModel: VM)


    override fun initLifeCycleOwner() {
        mBinding.lifecycleOwner = this
    }

    override fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    override fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, Constants.GALLERY_REQUEST_CODE)
    }

    override fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, ImageManger.save_image_in_provider(this))
        startActivityForResult(intent, Constants.CAMERA_REQUEST_CODE)
    }

}





