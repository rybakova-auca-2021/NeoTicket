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
    val detail_images: List<DetailImage>,
)

data class Genre(
    val name: String
)

data class DetailImage(
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
    val tickets_movie: List<MovieTicket>,
    val movie_title: String,
    val movie_image: String,
    val movie_cinema: String
)

data class MovieTicket(
    val id: Int,
    val seats_id: List<MovieSeat>,
    val movie_title: String,
    val movie_cinema: String,
    val movie_images: List<DetailImage>,
    val movie_data: String,
    val show_time: Int,
    val qr_code: String,
    val bar_code: String,
    val order: Int,
    val user: Int,
    var type: TicketType
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
    val seats_id: List<Int?>,
    val qr_code: String?,
    val bar_code: String?,
    val user: Int,
    val type: Int
)

data class Popular(
    val id: Int,
    val title: String,
    val image: String,
    val popularity: Int,
    val cinema_count: Int?,
    val place: Place?,
    val base_price: Int,
    val release_date: String?
)

data class Place(
    val name: String
)

data class TheaterImage(
    val image: String
)

data class Ticket(
    val id: Int,
    val user: Int,
    val theater_title: String?,
    val theater_image: String?,
    val theater_place: String?,
    val theater_date: String?,
    val sport_title: String?,
    val sport_image: String?,
    val sport_place: String?,
    val sport_date: String?,
    val movie_title: String?,
    val movie_image: String?,
    val movie_date: String?,
    val cinema: String?,
    val concert_title: String?,
    val concert_image: String?,
    val concert_place: String?,
    val concert_date: String?,
    val total_price: String,
    val total_tickets: Int,
    val tickets_theater: List<TheaterTicketDetail>?,
    val tickets_sport: List<SportTicketDetail>?,
    val tickets_movie: List<MovieTicketDetail>?,
    val tickets_concert: List<ConcertTicketDetail>?,
    val type: String?,
    val order_number: String
)

data class TheaterTicketDetail(
    val id: Int,
    val seats_id: List<Seat>,
    val theater_title: String,
    val theater_place: String,
    val theater_images: List<TheaterImage>,
    val qr_code: String,
    val bar_code: String,
    val show_time: Int,
    val order: Int,
    val user: Int
)

data class SportTicketDetail(
    val id: Int,
    val seats_id: List<Seat>,
    val sport_title: String,
    val sport_place: String,
    val sport_images: List<DetailImage>,
    val qr_code: String,
    val bar_code: String,
    val show_time: Int,
    val order: Int,
    val user: Int
)

data class ConcertTicketDetail(
    val id: Int,
    val seats_id: List<Seat>,
    val concert_title: String,
    val concert_place: String,
    val concert_images: List<DetailImage>,
    val qr_code: String,
    val bar_code: String,
    val show_time: Int,
    val order: Int,
    val user: Int,
    val concert_date: String
)

data class MovieTicketDetail(
    val id: Int,
    val seats_id: List<Seat>,
    val type: TicketType?,
    val qr_code: String,
    val bar_code: String,
    val show_time: Int,
    val order: Int,
    val user: Int
)

