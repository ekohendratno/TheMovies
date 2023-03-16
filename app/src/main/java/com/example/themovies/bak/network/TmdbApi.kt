package com.example.themovies.bak.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TmdbApi {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "2bc06d3a3bbe1779d5ee9248365fa98c"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

    val service = retrofit.create(TmdbApiService::class.java)
}