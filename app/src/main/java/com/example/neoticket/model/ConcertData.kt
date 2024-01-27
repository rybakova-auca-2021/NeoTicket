package com.example.neoticket.model

data class Concert(
    val id: Int,
    val title: String,
    val concert_date: String,
    val place: ConcertPlace,
    val detailImages: ConcertDetailImage,
    val show_times: ConcertShowTime
)

data class ConcertPlace(
    val name: String
)

data class ConcertDetailImage(
    val image: String
)

data class ConcertShowTime(
    val id: Int,
    val start_date: String,
    val base_ticket_price: String
)

data class ConcertOrderCheckout(
    val user: Int,
    val concert_order: Int
)

data class ConcertOrderCheckoutResponse(
    val id: Int,
    val user: Int,
    val concert_order: Int
)

data class ConcertOrder(
    val id: Int,
    val user: Int,
    val total_price: String,
    val tickets: List<ConcertTicket>
)

data class ConcertTicket(
    val id: String,
    val seats: List<Seat>,
    val concert_title: String,
    val concert_place: String,
    val concert_images: ConcertDetailImage,
    val concert_date: String,
    val show_time: Int,
    val order: Int,
    val user: Int
)

data class Seat(
    val id: Int,
    val row_number: Int,
    val seat_number: Int,
    val price: String,
    val is_available: Boolean
)

data class Section(
    val id: Int,
    val show_times: Int,
    val name: String,
    val concert_name: String,
    val place: String,
    val seats: List<Seat>
)

data class ConcertTicketCreate(
    val show_time: Int,
    val seats: List<Seat>,
    val order: Int,
    val user: Int
)


