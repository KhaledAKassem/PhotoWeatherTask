package com.github.khaledakassem.photo_weather.ui.fragments.photo_history_details

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.navigation.fragment.navArgs
import com.github.khaledakassem.photo_weather.R
import com.github.khaledakassem.photo_weather.common.GlideApp
import com.github.khaledakassem.photo_weather.databinding.FragmentPhotoHistoryDetailsBinding
import com.github.khaledakassem.photo_weather.ui.base.BaseFragment
import java.io.File
import java.io.FileInputStream



class PhotoHistoryDetailsFragment :PhotoHistoryDetailsView ,BaseFragment<PhotoHistoryDetailsViewModel,FragmentPhotoHistoryDetailsBinding>(PhotoHistoryDetailsViewModel::class.java ) {

    private val args: PhotoHistoryDetailsFragmentArgs by navArgs()

    override fun getLayoutRes()= R.layout.fragment_photo_history_details

    override fun initViewModel(viewModel: PhotoHistoryDetailsViewModel) {
            mBinding.viewModel=viewModel
    }

    override fun init(savedInstanceState: Bundle?) {
        initViews()
        initSocailMedia()
    }

    override fun initViews(){
        GlideApp.with(context!!)
            .load(args.imgUrl)
            .centerCrop()
            .into(mBinding.imgFullPhoto)
    }

    override fun initSocailMedia(){

        mBinding.fbShare.setOnClickListener{
            var bitmap: Bitmap? = null
            val f = File(args.imgUrl)
            val options = BitmapFactory.Options()
            options.inPreferredConfig = Bitmap.Config.ARGB_8888

            bitmap = BitmapFactory.decodeStream(FileInputStream(f), null, options)

            try {
                context!!.packageManager.getPackageInfo("com.facebook.katana", 0)
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.type = "image/*"
                intent.putExtra(Intent.EXTRA_STREAM, f)

                val openInChooser = Intent(intent)
                startActivity(openInChooser)
            } catch (e: Exception) {
                Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/appssquare/"))
            }
        }

        mBinding.twShare.setOnClickListener{

        }
    }
}