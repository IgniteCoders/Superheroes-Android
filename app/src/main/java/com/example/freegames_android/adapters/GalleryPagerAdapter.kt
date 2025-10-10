package com.example.freegames_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.get
import androidx.viewpager.widget.PagerAdapter
import com.example.freegames_android.data.Screenshot
import com.example.freegames_android.databinding.ItemGalleryPagerBinding
import com.example.freegames_android.databinding.ItemScreenshotBinding
import com.squareup.picasso.Picasso

class GalleryPagerAdapter(
    var items: List<Screenshot>,
    var selectedPosition: Int
) : PagerAdapter() {

    override fun getCount(): Int {
        return items.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view == obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val layoutInflater = LayoutInflater.from(container.context)
        val binding = ItemGalleryPagerBinding.inflate(layoutInflater, container, false)
        Picasso.get().load(items[position].image).into(binding.galleryImageView)
        container.addView(binding.root, position)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeViewAt(position)
    }
}