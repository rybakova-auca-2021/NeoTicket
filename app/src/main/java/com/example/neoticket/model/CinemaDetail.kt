package com.example.neoticket.model

data class CinemaDetail(
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val detail_images: List<MovieDetailImage>,
    val show_times: List<CinemaShowTime>
)


data class CinemaShowTime(
    val movie: Int,
    val start_date: String,
    val start_times: List<StartTime>,
    val movie_title: String,
    val movie_age_limit: Int,
    val movie_rating: Double
)

data class Cinema(
    val id: Int,
    val name: String,
    val address: String,
    val image: String
)