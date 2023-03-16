package com.example.themovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.adapters.MovieAdapter
import com.example.themovies.adapters.ReviewAdapter
import com.example.themovies.models.Review
import com.example.themovies.network.TMDbClient

class MainActivityReview : AppCompatActivity() {

    private var movieId: Int = 0
    private lateinit var progressBar: ProgressBar
    private lateinit var movieTitle: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapterReview: ReviewAdapter

    private var reviewList = mutableListOf<Review>()
    private var isLoading = false
    private var page = 1

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
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
        setContentView(R.layout.activity_movies_review)

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)


        toolbar?.findViewById<ImageView>(R.id.home)?.setOnClickListener(View.OnClickListener {

            onBackPressed()

        })

        movieId = intent.getIntExtra("movie_id", 0)


        // mengambil data judul film dari intent
        //movieTitle = intent.getStringExtra("movie_title")!!

        // mengatur judul halaman
        //title = "Ulasan Pengguna - $movieTitle"





        // inisialisasi RecyclerView dan adapter
        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapterReview = ReviewAdapter(reviewList)
        recyclerView.adapter = adapterReview
        showLoading()




        val client = TMDbClient()
        client.getMoviesReviewList(page = 1, movieId,
            onSuccess = { reviewList ->
                if(reviewList.isNullOrEmpty()){
                    println("Empty")
                    hideLoading()

                }else{
                    runOnUiThread {
                        adapterReview = ReviewAdapter(reviewList.toMutableList())
                        recyclerView.adapter = adapterReview


                        hideLoading()

                    }
                }
            },
            onError = { error ->
                println("Error: $error")
                hideLoading()
            }
        )

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    loadMoreData()
                }
            }
        })

    }

    private fun loadMoreData() {
        isLoading = true
        page++
        showLoading()

        val client = TMDbClient()
        client.getMoviesReviewList(page = page, movieId,
            onSuccess = { reviewList ->
                runOnUiThread {
                    val currentList = reviewList.toMutableList()

                    currentList.addAll( reviewList )
                    adapterReview.addMovies(currentList)

                    isLoading = false
                    hideLoading()
                }
            },
            onError = { error ->
                println("Error: $error")
                isLoading = false
                hideLoading()
            }
        )
    }
}