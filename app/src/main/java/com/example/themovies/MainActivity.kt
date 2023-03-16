package com.example.themovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.adapters.GenreAdapter
import com.example.themovies.network.TMDbClient
import com.example.themovies.models.Genre

class MainActivity : AppCompatActivity(), GenreAdapter.GenreAdapterClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GenreAdapter
    private lateinit var progressBar: ProgressBar

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = GenreAdapter(emptyList())
        recyclerView.adapter = adapter

        showLoading()
        val client = TMDbClient()
        client.getGenreList(page = 1,
            onSuccess = { genreList ->
                if( genreList.isNullOrEmpty()) {
                    println("Empty")

                    hideLoading()
                }else{

                    runOnUiThread {
                        adapter = GenreAdapter(genreList)
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

    }

    override fun onItemClicked(view: View, genre: Genre) {
        val intent = Intent(this@MainActivity, MainActivityMovies::class.java)
        intent.putExtra("genre_id", genre.id)
        intent.putExtra("genre_title", genre.name)

        startActivity(intent)
    }



}