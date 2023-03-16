package com.example.themovies

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.adapters.GenreAdapter
import com.example.themovies.adapters.ReviewAdapter
import com.example.themovies.adapters.TrailerAdapter
import com.example.themovies.models.Review
import com.example.themovies.models.Trailer
import com.example.themovies.network.TMDbClient
import com.squareup.picasso.Picasso

class MainActivityDetail : AppCompatActivity(), TrailerAdapter.TrailerAdapterClickListener {

    private var movieId: Int = 0
    private lateinit var progressBar: ProgressBar
    private lateinit var moviedetail: LinearLayout
    private lateinit var review: LinearLayout
    private lateinit var recyclerView2: RecyclerView
    private lateinit var recyclerView3: RecyclerView
    private lateinit var adapterTrailer: TrailerAdapter
    private lateinit var adapterReview: ReviewAdapter

    private fun showLoading() {
        moviedetail.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        moviedetail.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_details)

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)


        toolbar?.findViewById<ImageView>(R.id.home)?.setOnClickListener(View.OnClickListener {

            onBackPressed()

        })


        review = findViewById(R.id.review)
        moviedetail = findViewById(R.id.moviedetail)
        progressBar = findViewById(R.id.progressBar)


        recyclerView2 = findViewById(R.id.recyclerView2)
        recyclerView3 = findViewById(R.id.recyclerView3)

        movieId = intent.getIntExtra("movie_id", 0)


        showLoading()

        val client = TMDbClient()
        client.getMoviesDetail( movieId = movieId,
            onSuccess = { movie ->
                
                runOnUiThread {
                    val posterImageView = findViewById<ImageView>(R.id.poster)
                    val taglineTextView = findViewById<TextView>(R.id.tagline)
                    val titleTextView = findViewById<TextView>(R.id.title)
                    val overviewTextView = findViewById<TextView>(R.id.overview)
                    val releaseDateTextView = findViewById<TextView>(R.id.release_date)
                    val ratingTextView = findViewById<TextView>(R.id.rating)

                    taglineTextView.text = movie.tagline
                    titleTextView.text = movie.title
                    overviewTextView.text = movie.overview
                    releaseDateTextView.text = movie.releaseDate
                    ratingTextView.text = movie.voteAverage.toString()

                    val path = movie.posterPath;

                    if (!path.isNullOrEmpty()) {
                        val imageUrl = "https://image.tmdb.org/t/p/w500/$path"
                        Picasso.get().load(imageUrl).into(posterImageView)
                    }

                    hideLoading()

                }
            },
            onError = { error ->
                println("Error: $error")
            }
        )

        review.setOnClickListener(View.OnClickListener {

            val intent = Intent(this@MainActivityDetail, MainActivityReview::class.java)
            intent.putExtra("movie_id", movieId)

            startActivity(intent)

        })


        //Untuk Trailer
        recyclerView2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL ,false)
        adapterTrailer = TrailerAdapter(emptyList())
        recyclerView2.adapter = adapterTrailer
        client.getMoviesTrailerList(movieId = movieId,
            onSuccess = { trailerList ->
                runOnUiThread {
                    adapterTrailer = TrailerAdapter(trailerList)
                    recyclerView2.adapter = adapterTrailer


                    adapterTrailer.listener = this


                }
            },
            onError = { error ->
                println("Error: $error")
            }
        )

        //Untuk Review
        recyclerView3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        recyclerView3.adapter = adapterTrailer
        client.getMoviesReviewList(page =1, movieId = movieId,
            onSuccess = { reviewList ->
                runOnUiThread {
                    adapterReview = ReviewAdapter(reviewList as MutableList<Review>)
                    recyclerView3.adapter = adapterReview




                }
            },
            onError = { error ->
                println("Error: $error")
            }
        )


    }

    override fun onItemClicked(view: View, trailer: Trailer) {

        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse( "https://www.youtube.com/watch?v=" + trailer.key!!)
        startActivity(Intent.createChooser(intent, "View Trailer:"))
    }

}