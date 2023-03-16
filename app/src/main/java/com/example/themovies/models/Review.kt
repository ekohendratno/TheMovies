package com.example.themovies.models

data class Review(
    val author: String,
    val author_details: ReviewDetails,
    val content: String,
    val created_at: String,
    val id: String,
    val updated_at: String,
    val url: String
)

data class ReviewDetails(
    val name: String,
    val username: String,
    val avatar_path: String,
    val rating: String,
)
