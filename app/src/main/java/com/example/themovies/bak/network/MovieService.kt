package com.example.themovies.bak.network

import com.example.themovies.bak.GenreSearchResult
import com.example.themovies.bak.MovieSearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int,
        @Query("language") language: String = "en-US"
    ): MovieSearchResult

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): GenreSearchResult
}