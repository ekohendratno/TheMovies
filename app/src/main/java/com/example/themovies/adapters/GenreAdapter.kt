package com.example.themovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.R
import com.example.themovies.models.Genre

class GenreAdapter(private val genres: List<Genre>) : RecyclerView.Adapter<GenreAdapter.GenreViewHolder>() {

    var listener: GenreAdapterClickListener? = null

    class GenreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val genreNameTextView: TextView = itemView.findViewById(R.id.title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_genre, parent, false)
        return GenreViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = genres[position]
        holder.genreNameTextView.text = genre.name


        holder.itemView.setOnClickListener {
            listener?.onItemClicked(it, genre )
        }
    }

    override fun getItemCount() = genres.size

    interface GenreAdapterClickListener {

        // method yang akan dipanggil di MainActivity
        fun onItemClicked(view: View, genre: Genre)

    }
}