package com.example.themovies.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.R
import com.example.themovies.models.Trailer
import com.squareup.picasso.Picasso

class TrailerAdapter(private val genres: List<Trailer>) : RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder>() {

    var listener: TrailerAdapterClickListener? = null

    class TrailerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster: ImageView = itemView.findViewById(R.id.poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrailerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_trailer, parent, false)
        return TrailerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TrailerViewHolder, position: Int) {
        val trailer = genres[position]
        if (trailer.key != null) {
            val imageUrl = "https://img.youtube.com/vi/${trailer.key}/0.jpg"

            Log.e("x",imageUrl)

            Picasso.get().load(imageUrl).into(holder.poster)
        }


        holder.itemView.setOnClickListener {
            listener?.onItemClicked(it, trailer )
        }
    }

    override fun getItemCount() = genres.size

    interface TrailerAdapterClickListener {

        // method yang akan dipanggil di MainActivity
        fun onItemClicked(view: View, trailer: Trailer)

    }
}