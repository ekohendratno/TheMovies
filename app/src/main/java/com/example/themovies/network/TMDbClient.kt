package com.example.themovies.network

import android.util.Log
import com.example.themovies.constants.API_KEY
import com.example.themovies.models.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class TMDbClient() {
    private val client = OkHttpClient()

    fun getGenreList(page: Int, onSuccess: (List<Genre>) -> Unit, onError: (String) -> Unit) {
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/genre/movie/list?api_key=$API_KEY&page=$page&language=en-US")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body?.string()
                val jsonObject = JSONObject(jsonString)
                val genresArray = jsonObject.getJSONArray("genres")

                val genreList = mutableListOf<Genre>()
                for (i in 0 until genresArray.length()) {
                    val genreObject = genresArray.getJSONObject(i)
                    val genre = Genre(
                        id = genreObject.getInt("id"),
                        name = genreObject.getString("name")
                    )
                    genreList.add(genre)
                }

                onSuccess(genreList)
            }

            override fun onFailure(call: Call, e: IOException) {
                onError(e.message ?: "Unknown error")
            }
        })
    }

    fun getMoviesList(
        page: Int,
        onSuccess: (List<Movie>) -> Unit,
        onError: (String) -> Unit,
        genreId: Int
    ) {
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&with_genres=$genreId&page=$page&language=en-US")
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body?.string()
                val jsonObject = JSONObject(jsonString)
                val moviesArray = jsonObject.getJSONArray("results")

                val movieList = mutableListOf<Movie>()
                for (i in 0 until moviesArray.length()) {
                    val movieObject = moviesArray.getJSONObject(i)
                    val movie = Movie(
                        id = movieObject.getInt("id"),
                        title = movieObject.getString("title"),
                        overview = movieObject.getString("overview"),
                        posterPath = movieObject.getString("poster_path"),
                        releaseDate = movieObject.getString("release_date"),
                        voteAverage = movieObject.getDouble("vote_average")
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



    fun getMoviesDetail(
        onSuccess: (MovieDetail) -> Unit,
        onError: (String) -> Unit,
        movieId: Int
    ) {
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/$movieId?api_key=$API_KEY&language=en-US")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body?.string()
                val jsonObject = JSONObject(jsonString)

                val id = jsonObject.getInt("id")
                val tagline = jsonObject.getString("tagline")
                val title = jsonObject.getString("title")
                val overview = jsonObject.getString("overview")
                val releaseDate = jsonObject.getString("release_date")
                val rating = jsonObject.getDouble("vote_average")
                val poster = jsonObject.getString("poster_path")

                onSuccess(MovieDetail(
                    id = id,
                    tagline = tagline,
                    title = title,
                    overview = overview,
                    releaseDate = releaseDate,
                    voteAverage = rating,
                    posterPath = poster
                ))
            }

            override fun onFailure(call: Call, e: IOException) {
                onError(e.message ?: "Unknown error")
            }
        })
    }



    fun getMoviesTrailerList(
        onSuccess: (List<Trailer>) -> Unit,
        onError: (String) -> Unit,
        movieId: Int
    ) {
        val request = Request.Builder()
            .url("http://api.themoviedb.org/3/movie/$movieId/videos?api_key=$API_KEY&language=en-US")
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body?.string()
                val jsonObject = JSONObject(jsonString)
                val moviesArray = jsonObject.getJSONArray("results")


                val trailerList = mutableListOf<Trailer>()
                for (i in 0 until moviesArray.length()) {
                    val movieObject = moviesArray.getJSONObject(i)
                    val trailer = Trailer(
                        id = movieObject.getString("id"),
                        key = movieObject.getString("key"),
                        name = movieObject.getString("name"),
                        site = movieObject.getString("site"),
                        size = movieObject.getString("size"),
                        type = movieObject.getString("type"),
                        official = movieObject.getBoolean("official")
                    )
                    trailerList.add(trailer)
                }

                onSuccess(trailerList)
            }

            override fun onFailure(call: Call, e: IOException) {
                onError(e.message ?: "Unknown error")
            }
        })
    }



    fun getMoviesReviewList(
        page: Int,
        movieId: Int,
        onSuccess: (List<Review>) -> Unit,
        onError: (String) -> Unit
    ) {
        val request = Request.Builder()
            .url("https://api.themoviedb.org/3/movie/$movieId/reviews?api_key=$API_KEY&page=$page&language=en-US")
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val jsonString = response.body?.string()
                val jsonObject = JSONObject(jsonString)
                val moviesArray = jsonObject.getJSONArray("results")


                val reviewList = mutableListOf<Review>()
                for (i in 0 until moviesArray.length()) {
                    val movieObject = moviesArray.getJSONObject(i)

                    val author_details = movieObject.getJSONObject("author_details")


                    val trailer = Review(
                        author = movieObject.getString("author"),
                        author_details = ReviewDetails(
                            name = author_details.getString("name"),
                            username = author_details.getString("username"),
                            avatar_path = author_details.getString("avatar_path"),
                            rating = author_details.getString("rating"),
                        ),
                        content = movieObject.getString("content"),
                        created_at = movieObject.getString("created_at"),
                        id = movieObject.getString("id"),
                        updated_at = movieObject.getString("updated_at"),
                        url = movieObject.getString("url")
                    )
                    reviewList.add(trailer)
                }

                onSuccess(reviewList)
            }

            override fun onFailure(call: Call, e: IOException) {
                onError(e.message ?: "Unknown error")
            }
        })
    }
}