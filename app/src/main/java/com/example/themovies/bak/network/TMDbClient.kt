package com.example.themovies.bak.network

import com.example.themovies.bak.Movie1
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class TMDbClient(private val apiKey: String) {
    private val client = OkHttpClient()

    fun getMovieList(page: Int, onSuccess: (List<Movie1>) -> Unit, onError: (String) -> Unit) {
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/popular?api_key=$apiKey&page=$page")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    onError("Error: ${response.code}")
                    return
                }

                val jsonString = response.body?.string()
                val jsonObject = JSONObject(jsonString)
                val resultsArray = jsonObject.getJSONArray("results")

                val movieList = mutableListOf<Movie1>()
                for (i in 0 until resultsArray.length()) {
                    val movieObject = resultsArray.getJSONObject(i)
                    val movie = Movie1(
                        id = movieObject.getInt("id"),
                        title = movieObject.getString("title"),
                        releaseDate = movieObject.getString("release_date"),
                        overview = movieObject.getString("overview"),
                        posterUrl = "https://image.tmdb.org/t/p/w500${movieObject.getString("poster_path")}"
                    )
                    movieList.add(movie)
                }

                onSuccess(movieList)
            }

            override fun onFailure(call: Call, e: IOException) {
                onError(e.message ?: "Unknown error")
            }
        })
    }
}