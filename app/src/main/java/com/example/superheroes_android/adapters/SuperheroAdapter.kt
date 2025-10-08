package com.example.superheroes_android.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.superheroes_android.R
import com.example.superheroes_android.data.Superhero
import com.squareup.picasso.Picasso

class SuperheroAdapter(
    var items: List<Superhero>,
    val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<SuperheroViewHolder>() {

    // Cual es la vista para los elementos
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuperheroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_superhero, parent, false)
        return SuperheroViewHolder(view)
    }

    // Cuales son los datos para el elemento
    override fun onBindViewHolder(holder: SuperheroViewHolder, position: Int) {
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

    fun updateItems(items: List<Superhero>) {
        this.items = items
        notifyDataSetChanged()
    }
}

class SuperheroViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val nameTextView: TextView = view.findViewById(R.id.nameTextView)
    val avatarImageView: ImageView = view.findViewById(R.id.avatarImageView)

    fun render(superhero: Superhero) {
        nameTextView.text = superhero.name
        //Log.d("API", superhero.image.url)
        //Log.d("API", superhero.id)
        Picasso.get().load("https://i.imgur.com/hPsu2lH.png").into(avatarImageView)
    }
}