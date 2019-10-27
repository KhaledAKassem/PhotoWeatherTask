package com.github.khaledakassem.photo_weather.ui.fragments.photo_history_details

import android.content.Intent
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.github.khaledakassem.photo_weather.common.GlideApp
import com.github.khaledakassem.photo_weather.databinding.FragmentPhotoHistoryDetailsBinding
import com.github.khaledakassem.photo_weather.ui.base.BaseFragment
import com.facebook.share.model.SharePhotoContent
import com.facebook.share.model.SharePhoto
import com.facebook.*
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import android.graphics.Bitmap
import com.facebook.share.Sharer
import com.facebook.share.widget.ShareDialog
import com.github.khaledakassem.photo_weather.common.extensions.confirmMsg
import com.github.khaledakassem.photo_weather.common.extensions.errorMsg
import com.squareup.picasso.Target
import java.io.File
import java.lang.Exception
import com.github.khaledakassem.photo_weather.R
import com.github.khaledakassem.photo_weather.common.extensions.loadImg


class PhotoHistoryDetailsFragment : PhotoHistoryDetailsView,
    BaseFragment<PhotoHistoryDetailsViewModel, FragmentPhotoHistoryDetailsBinding>(
        PhotoHistoryDetailsViewModel::class.java
    ) {

    private val args: PhotoHistoryDetailsFragmentArgs by navArgs()
    private lateinit var callbackManager: CallbackManager
    var shareDialog: ShareDialog? = null

    private val target = object : Target {
        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
            errorMsg(e.toString())
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            confirmMsg("Your image Prepared for uploading pls wait ....")
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


    override fun getLayoutRes() = R.layout.fragment_photo_history_details

    override fun initViewModel(viewModel: PhotoHistoryDetailsViewModel) {
        mBinding.viewModel = viewModel
    }


    override fun init(savedInstanceState: Bundle?) {
        initViews()
        initSocialMedia()
        shareDialog = ShareDialog(this)
        callbackManager = CallbackManager.Factory.create()


    }

    override fun initViews() {
        GlideApp.with(context!!)
            .load(args.imgUrl)
            .centerCrop()
            .into(mBinding.imgFullPhoto)

    }

    override fun initSocialMedia() {
        mBinding.fbShare.setOnClickListener {
            Picasso.get().load(File(args.imgUrl)).into(target)
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
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

}