package com.example.freegames_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freegames_android.R
import com.example.freegames_android.data.Screenshot
import com.example.freegames_android.databinding.ItemGalleryBinding
import com.squareup.picasso.Picasso

class GalleryAdapter(
    var items: List<Screenshot>,
    var selectedPosition: Int,
    val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<GalleryViewHolder>() {

    // Cual es la vista para los elementos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGalleryBinding.inflate(layoutInflater, parent, false)
        return GalleryViewHolder(binding)
    }

    // Cuales son los datos para el elemento
    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.setSelected(position == selectedPosition)
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }

    // Cuantos elementos se quieren listar?
    override fun getItemCount(): Int {
        return items.size
    }

    fun changeSelectedPosition(position: Int) {
        val oldSelected = selectedPosition
        selectedPosition = position
        notifyItemChanged(oldSelected)
        notifyItemChanged(selectedPosition)
    }
}

class GalleryViewHolder(val binding: ItemGalleryBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(screenshot: Screenshot) {
        Picasso.get().load(screenshot.image).placeholder(R.drawable.bg_image_placeholder).into(binding.screenshotImageView)
    }

    fun setSelected(selected: Boolean) {
        if (selected) {
            itemView.setBackgroundResource(R.color.md_theme_primary)
        } else {
            itemView.setBackgroundResource(0)
        }
    }
}