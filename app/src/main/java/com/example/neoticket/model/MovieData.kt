package com.example.neoticket.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val image: String?,
    val link_to_video: String,
    val rating: Double,
    val genres: List<Genre>,
    val country_of_origin: String,
    val film_duration: String,
    val age_limit: Int,
    val description: String,
    val main_actors: String,
    val director: String,
    val release_date: String?,
    val detail_images: List<MovieDetailImage>,
    val cinema: Cinema
)

data class Genre(
    val name: String
)

data class MovieDetailImage(
    val image: String
)

data class Cinema(
    val name: String
)

data class Movie(
    val id: Int,
    val title: String,
    val age_limit: Int,
    val rating: Int,
    val image: String
)

data class ShowTime(
    val movie: Int,
    val cinema: String,
    val start_date: String,
    val start_times: StartTime
)

data class StartTime(
    val id: Int,
    val time: String,
    val base_ticket_price: String
)