package com.example.themovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.R
import com.example.themovies.models.Movie
import com.squareup.picasso.Picasso

class MovieAdapter(private var movies: MutableList<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    var listener: MovieAdapterClickListener? = null

    fun addMovies(newMovies: List<Movie>) {
        val startInsertPosition = movies.size
        movies.addAll(newMovies)
        notifyItemRangeInserted(startInsertPosition, newMovies.size)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val rating: TextView = itemView.findViewById(R.id.rating)
        val poster: ImageView = itemView.findViewById(R.id.poster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movies, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.title
        holder.rating.text = movie.voteAverage.toString()
        if (movie.posterPath != null) {
            val imageUrl = "https://image.tmdb.org/t/p/w500/${movie.posterPath}"
            Picasso.get().load(imageUrl).into(holder.poster)
        }

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(it, movie )
        }
    }

    override fun getItemCount(): Int = movies.size

    interface MovieAdapterClickListener {

        // method yang akan dipanggil di MainActivity
        fun onItemClicked(view: View, movie: Movie)

    }
}