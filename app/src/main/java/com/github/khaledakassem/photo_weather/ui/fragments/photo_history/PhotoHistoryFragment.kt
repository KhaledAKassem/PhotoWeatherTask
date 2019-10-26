package com.github.khaledakassem.photo_weather.ui.fragments.photo_history

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.khaledakassem.photo_weather.R
import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos
import com.github.khaledakassem.photo_weather.databinding.FragmentPhotoHistoryBinding
import com.github.khaledakassem.photo_weather.ui.adapters.PhotosListAdapter
import com.github.khaledakassem.photo_weather.ui.base.BaseFragment

class PhotoHistoryFragment :PhotoHistoryView,
    BaseFragment<PhotoHistoryViewModel, FragmentPhotoHistoryBinding>(PhotoHistoryViewModel::class.java) {

    override fun getLayoutRes()= R.layout.fragment_photo_history

    override fun init(savedInstanceState: Bundle?) {
            getAllPhotos()
    }

    override fun initViewModel(viewModel: PhotoHistoryViewModel) {
            mBinding.viewModel=viewModel
    }

    override fun getAllPhotos() {
        viewModel.getAllPhotos().observe(this, Observer (::initPhotosCycle))

    }

    override fun initPhotosCycle(list: List<Photos>?) {
        mBinding.rcPhotosList.layoutManager = LinearLayoutManager(context!! )
        mBinding.rcPhotosList.adapter = PhotosListAdapter(list!!, this,context!!)
    }

    override fun setOnPhotoClick(id: Int, imageUrl: String?) {

        val action =
            PhotoHistoryFragmentDirections.actionPhotoHistoryFragmentToPhotoHistoryDetailsFragment(imageUrl!!)
        findNavController().navigate(action)
    }

}