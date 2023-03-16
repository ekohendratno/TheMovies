package com.example.themovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.adapters.MovieAdapter
import com.example.themovies.models.Movie
import com.example.themovies.network.TMDbClient

class MainActivityMovies : AppCompatActivity(), MovieAdapter.MovieAdapterClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private var genreId: Int = 0
    private lateinit var genreTitle: String
    private lateinit var progressBar: ProgressBar

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
        setContentView(R.layout.activity_movies)

        genreId = intent.getIntExtra("genre_id", 0)
        genreTitle = intent.getStringExtra("genre_title").toString()

        val toolbar = findViewById(R.id.toolbar) as Toolbar?
        setSupportActionBar(toolbar)


        toolbar?.findViewById<TextView>(R.id.title)?.setText(genreTitle)
        toolbar?.findViewById<ImageView>(R.id.home)?.setOnClickListener(View.OnClickListener {

            onBackPressed()

        })



        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        //recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        //adapter = MovieAdapter(emptyList())
        //recyclerView.adapter = adapter


        showLoading()




        val client = TMDbClient()
        client.getMoviesList(page = 1, genreId = genreId,
            onSuccess = { movieList ->
                if(movieList.isNullOrEmpty()){
                    println("Empty")
                    hideLoading()

                }else{

                    runOnUiThread {
                        adapter = MovieAdapter(movieList.toMutableList())
                        recyclerView.adapter = adapter


                        adapter.listener = this

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

        val client = TMDbClient()
        client.getMoviesList(page = page, genreId = genreId,
            onSuccess = { movieList ->
                runOnUiThread {
                    val currentList = movieList.toMutableList()

                    currentList.addAll( movieList )
                    adapter.addMovies(currentList)

                    isLoading = false
                }
            },
            onError = { error ->
                println("Error: $error")
                isLoading = false
            }
        )
    }

    override fun onItemClicked(view: View, movie: Movie) {

        val intent = Intent(this@MainActivityMovies, MainActivityDetail::class.java)
        intent.putExtra("movie_id", movie.id)

        startActivity(intent)
    }


}