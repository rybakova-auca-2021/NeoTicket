package com.example.neoticket.model

data class SportData(
    val id: Int,
    val title: String,
    val description: String,
    val sport_date: String,
    val place: SportPlace,
    val detail_images: List<DetailImage>,
    val show_times: List<CombinedShowTime>
)

data class SportDetail(
    val id: Int,
    val title: String,
    val description: String,
    val sport_date: String,
    val time: String,
    val place: SportPlace,
    val age_limit: Int
)

data class SportPlace(
    val name: String
)

data class SportDetailImage(
    val image: String
)

data class SportShowTime(
    val id: Int,
    val start_date: String,
    val base_ticket_price: String
)

data class SportOrderCheckout(
    val user: Int,
    val sport_order: Int
)

data class SportOrderCheckoutResponse(
    val id: Int,
    val user: Int,
    val sport_order: Int
)

data class SportSeat(
    val id: Int,
    val row_number: Int,
    val seat_number: Int,
    val price: String,
    val is_available: Boolean
)

data class SportTicket(
    val id: Int,
    val seats: List<SportSeat>,
    val sport_title: String,
    val sport_place: String,
    val sport_images: List<SportDetailImage>,
    val sport_date: String,
    val show_time: Int,
    val order: Int,
    val user: Int
)

data class SportOrder(
    val id: Int,
    val user: Int,
    val total_price: String,
    val tickets: List<SportTicket>
)

data class SportSection(
    val id: Int,
    val show_time: Int,
    val name: String,
    val sport_name: String,
    val place: String,
    val sport_seats: List<Seat>
)

data class SportTicketCreate(
    val show_time: Int,
    val seats: List<Int>,
    val user: Int
)
