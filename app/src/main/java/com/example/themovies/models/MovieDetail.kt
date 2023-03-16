package com.example.themovies.models

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    val id: Int,
    val overview: String?,
    val posterPath: String?,
    val releaseDate: String?,
    val title: String,
    val tagline: String,
    val voteAverage: Double
)

/*
data class MovieDetail(
    val adult: Boolean,
    val backdropPath: String?,
    val collection: Collection?,
    val budget: Int,
    val genres: List<Genre>,
    val homepage: String?,
    val id: Int,
    val imdbId: String?,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String?,
    val popularity: Double,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany>,
    val productionCountries: List<ProductionCountry>,
    val releaseDate: String?,
    val revenue: Int,
    val runtime: Int?,
    val spokenLanguages: List<SpokenLanguage>,
    val status: String?,
    val tagline: String?,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)*/

data class Collection(
    val id: Int,
    val name: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("backdrop_path")
    val backdropPath: String?
)

data class ProductionCompany(
    val id: Int,
    @SerializedName("logo_path")
    val logoPath: String?,
    val name: String,
    @SerializedName("origin_country")
    val originCountry: String
)

data class ProductionCountry(
    @SerializedName("iso_3166_1")
    val iso31661: String,
    val name: String
)

data class SpokenLanguage(
    @SerializedName("english_name")
    val englishName: String
)