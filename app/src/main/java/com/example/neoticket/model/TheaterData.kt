package com.example.neoticket.model

data class Theater(
    val id: Int,
    val title: String,
    val description: String,
    val theater_date: String?,
    val place: TheaterPlace,
    val detail_images: List<DetailImage>,
    val show_times: List<CombinedShowTime>
)

data class TheaterDetail(
    val id: Int,
    val description: String,
    val year_of_issue: String,
    val country_of_origin: String,
    val theater_date: String?,
    val time: String,
    val composer: String,
    val main_actors: String,
    val genre: TheaterGenre,
    val duration: String,
    val age_limit: Int
)

data class TheaterPlace(
    val name: String
)

data class TheaterGenre(
    val name: String
)

data class TheaterDetailImage(
    val image: String
)

data class CombinedShowTime(
    val id: Int,
    val start_date: String,
    val base_ticket_price: String
)

data class TheaterOrderCheckout(
    val user: Int,
    val theater_order: Int
)

data class TheaterOrderRefund(
    val user: Int,
    val theater_order: Int,
    val device_name: String
)

data class TheaterOrderRefundResponse(
    val id: Int,
    val user: Int,
    val theater_order: Int,
    val device_id: String
)

data class TheaterOrderCheckoutResponse(
    val id: Int,
    val user: Int,
    val theater_order: Int
)

data class TheaterOrder(
    val id: Int,
    val user: Int,
    val total_price: String,
    val tickets_theater: List<TheaterTicket>
)

data class TheaterTicket(
    val id: Int,
    val seats_id: List<TheaterSeat>,
    val theater_title: String,
    val theater_place: String,
    val theater_images: List<TheaterDetailImage>,
    val theater_date: String?,
    val qr_code: String,
    val bar_code: String,
    val show_time: Int,
    val order: Int,
    val user: Int
)

data class TheaterSeat(
    val id: Int,
    val row_number: Int,
    val seat_number: Int,
    val price: String,
    val is_available: Boolean
)

data class TheaterSection(
    val id: Int,
    val show_time: Int,
    val name: String,
    val theater_name: String,
    val place: String,
    val theater_seats: List<Seat>
)

data class TheaterTicketCreate(
    val show_time: Int,
    val seats_id: List<Int?>,
    val qr_code: String,
    val bar_code: String,
    val user: Int
)