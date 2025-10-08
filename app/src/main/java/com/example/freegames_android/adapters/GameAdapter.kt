package com.example.freegames_android.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.freegames_android.data.Game
import com.example.freegames_android.databinding.ItemGameBinding
import com.squareup.picasso.Picasso

class GameAdapter(
    var items: List<Game>,
    val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<GameViewHolder>() {

    // Cual es la vista para los elementos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGameBinding.inflate(layoutInflater, parent, false)
        return GameViewHolder(binding)
    }

    // Cuales son los datos para el elemento
    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
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

    fun updateItems(items: List<Game>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class GameViewHolder(val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {

    fun render(game: Game) {
        binding.nameTextView.text = game.title
        Picasso.get().load(game.thumbnail).into(binding.thumbnailImageView)
    }
}