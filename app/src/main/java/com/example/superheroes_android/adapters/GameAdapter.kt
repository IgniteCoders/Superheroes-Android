package com.example.superheroes_android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroes_android.R
import com.example.superheroes_android.data.Game
import com.squareup.picasso.Picasso

class GameAdapter(
    var items: List<Game>,
    val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<GameViewHolder>() {

    // Cual es la vista para los elementos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        return GameViewHolder(view)
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

class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)

    fun render(game: Game) {
        nameTextView.text = game.title
        Picasso.get().load(game.thumbnail).into(avatarImageView)
    }
}