package com.github.khaledakassem.photo_weather.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.khaledakassem.photo_weather.common.GlideApp
import com.github.khaledakassem.photo_weather.data.database.schema.entities.Photos
import com.github.khaledakassem.photo_weather.databinding.ItemHistoryPhotoBinding
import com.github.khaledakassem.photo_weather.ui.base.BaseViewHolder
import com.github.khaledakassem.photo_weather.ui.fragments.photo_history.PhotoHistoryView


class PhotosListAdapter(private val photos : List<Photos>,
                             private val photoHistoryView: PhotoHistoryView,val context:Context) :
    RecyclerView.Adapter<PhotosListAdapter.ItemHistoryPhoto>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHistoryPhoto {
        val binding : ItemHistoryPhotoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), com.github.khaledakassem.photo_weather.R.layout.item_history_photo,
            parent, false)

        return ItemHistoryPhoto(binding)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: ItemHistoryPhoto, position: Int) {


        GlideApp.with(context)
            .load(photos[position].imageUrl)
            .centerCrop()
            .into(holder.binding.imgHistory)

        holder.binding.root.setOnClickListener {
            photoHistoryView.setOnPhotoClick(photos[position].id, photos[position].imageUrl)
        }
    }

    inner class ItemHistoryPhoto(binding: ItemHistoryPhotoBinding) :
        BaseViewHolder<ItemHistoryPhotoBinding>(binding)
}


