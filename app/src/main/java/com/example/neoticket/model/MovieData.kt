package com.example.neoticket.model

import com.google.android.datatransport.cct.StringMerger

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
)

data class Genre(
    val name: String
)

data class MovieDetailImage(
    val image: String
)

data class Movie(
    val id: Int,
    val title: String,
    val age_limit: Int,
    val rating: Double,
    val image: String
)

data class ShowTime(
    val id: Int,
    val start_date: String,
    val movie: Int,
    val start_times: List<StartTime>,
    val cinema: Cinema,
)

data class StartTime(
    val id: Int,
    val time: String,
    val base_ticket_price: String
)

data class MovieOrderCheckout(
    val user: Int,
    val movie_order: Int
)

data class MovieOrderCheckoutResponse(
    val id: Int,
    val user: Int,
    val movie_order: Int
)

data class MovieOrder(
    val id: Int,
    val user: Int,
    val total_price: String,
    val tickets: List<MovieTicket>
)

data class MovieTicket(
    val id: Int,
    val seats: List<MovieSeat>,
    val movie_title: String,
    val movie_cinema: String,
    val movie_images: List<MovieDetailImage>,
    val movie_data: String,
    val show_time: Int,
    val order: Int,
    val user: Int,
    val type: Int
)

data class MovieSeat(
    val id: Int,
    val row_number: Int,
    val seat_number: Int,
    val is_available: Boolean
)

data class MovieShowTime(
    val start_date: String,
    val start_times: StartTime,
    val cinemas: List<Cinema>
)

data class StartTimeDetail(
    val id: Int,
    val movie_title: String,
    val cinema_name: String,
    val seats: List<MovieSeat>
)

data class TicketType(
    val id: Int,
    val name: String,
    val price: String
)

data class MovieTicketCreate(
    val show_time: Int,
    val seats: List<Int>,
    val order: Int,
    val user: Int,
    val type: Int
)