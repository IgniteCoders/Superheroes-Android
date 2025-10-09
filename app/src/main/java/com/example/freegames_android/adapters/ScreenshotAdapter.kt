package com.example.freegames_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freegames_android.R
import com.example.freegames_android.data.Screenshot
import com.example.freegames_android.databinding.ItemScreenshotBinding
import com.squareup.picasso.Picasso

class ScreenshotAdapter(
    var items: List<Screenshot>,
    val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<ScreenshotViewHolder>() {

    // Cual es la vista para los elementos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScreenshotViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemScreenshotBinding.inflate(layoutInflater, parent, false)
        return ScreenshotViewHolder(binding)
    }

    // Cuales son los datos para el elemento
    override fun onBindViewHolder(holder: ScreenshotViewHolder, position: Int) {
        val item = items[position]
        holder.render(item)
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }
    }

    // Cuantos elementos se quieren listar?
    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(items: List<Screenshot>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class ScreenshotViewHolder(val binding: ItemScreenshotBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(screenshot: Screenshot) {
        Picasso.get().load(screenshot.image).placeholder(R.drawable.bg_image_placeholder).into(binding.screenshotImageView)
    }
}