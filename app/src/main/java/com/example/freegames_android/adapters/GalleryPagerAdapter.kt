package com.example.freegames_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freegames_android.data.Screenshot
import com.example.freegames_android.databinding.ItemGalleryPagerBinding
import com.squareup.picasso.Picasso

class GalleryPagerAdapter(var items: List<Screenshot>) : RecyclerView.Adapter<GalleryPagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryPagerViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGalleryPagerBinding.inflate(layoutInflater, parent, false)
        return GalleryPagerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GalleryPagerViewHolder, position: Int) {
        val item = items[position]
        holder.render(item.image)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

class GalleryPagerViewHolder(val binding: ItemGalleryPagerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun render(image: String) {
        Picasso.get().load(image).into(binding.galleryImageView)
    }
}