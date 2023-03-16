package com.example.themovies.bak

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.themovies.R
import com.example.themovies.bak.network.TMDbClient

class MainActivity2 : AppCompatActivity() {

    private lateinit var recyclerView : RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val apiKey = "2bc06d3a3bbe1779d5ee9248365fa98c"
        val client = TMDbClient(apiKey)

        client.getMovieList(page = 1,
            onSuccess = { movies ->
                for (movie in movies) {
                    println(movie.title)
                }
            },
            onError = { error ->
                println("Error: $error")
            }
        )



    }

}