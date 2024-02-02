package com.example.neoticket.model

data class CinemaDetail(
    val id: Int,
    val name: String,
    val description: String,
    val detail_images: CinemaDetailImage,
    val show_times: CinemaShowTime
)

data class CinemaDetailImage(
    val image: String
)

data class CinemaShowTime(
    val movie: Int,
    val start_date: String,
    val start_times: StartTime,
    val movie_title: String,
    val movie_age_limit: Int,
    val movie_rating: Int
)

data class Cinema(
    val id: Int,
    val name: String,
    val address: String,
    val image: String
)