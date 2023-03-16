package com.example.themovies.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.R
import com.example.themovies.models.Review

class ReviewAdapter(private val reviewList: MutableList<Review>) :
    RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {
    var listener: ReviewAdapterClickListener? = null

    fun addMovies(newReviews: List<Review>) {
        val startInsertPosition = reviewList.size
        reviewList.addAll(newReviews)
        notifyItemRangeInserted(startInsertPosition, newReviews.size)
    }

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val authorTextView: TextView = itemView.findViewById(R.id.author)
        private val contentTextView: TextView = itemView.findViewById(R.id.ulasan)

        fun bind(review: Review) {
            authorTextView.text = review.author
            contentTextView.text = review.content
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviewList[position]
        holder.bind(review)

        holder.itemView.setOnClickListener {
            listener?.onItemClicked(it, review )
        }
    }

    override fun getItemCount(): Int = reviewList.size

    interface ReviewAdapterClickListener {

        // method yang akan dipanggil di MainActivity
        fun onItemClicked(view: View, review: Review)

    }
}