package com.example.neoticket.model

data class Theater(
    val id: Int,
    val title: String,
    val theater_date: String?,
    val place: TheaterPlace,
    val detail_images: List<TheaterDetailImage>,
    val show_times: List<TheaterShowTime>
)

data class TheaterPlace(
    val name: String
)

data class TheaterDetailImage(
    val image: String
)

data class TheaterShowTime(
    val id: Int,
    val title: String,
    val readOnly: Boolean,
    val start_date: String,
    val base_ticket_price: String
)

data class TheaterOrderCheckout(
    val user: Int,
    val theater_order: Int
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
    val tickets: List<TheaterTicket>
)

data class TheaterTicket(
    val id: Int,
    val seats: List<TheaterSeat>,
    val theater_title: String,
    val theater_place: String,
    val theater_images: List<TheaterDetailImage>,
    val theater_date: String?,
    val show_time: Int,
    val order: Int?,
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
    val seats: List<TheaterSeat>
)

data class TheaterTicketCreate(
    val show_time: Int,
    val seats: List<Seat>,
    val order: Int,
    val user: Int
)