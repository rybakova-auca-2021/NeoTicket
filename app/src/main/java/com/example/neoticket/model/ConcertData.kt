package com.example.neoticket.model

data class Concert(
    val id: Int,
    val title: String,
    val description: String,
    val concert_date: String,
    val place: ConcertPlace,
    val detail_images: List<DetailImage>,
    val show_times: List<CombinedShowTime>
)

data class ConcertPlace(
    val name: String
)

data class ConcertDetailImage(
    val image: String
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

data class ConcertOrderRefund(
    val user: Int,
    val concert_order: Int,
    val device_name: String
)

data class ConcertOrderRefundResponse(
    val id: Int,
    val user: Int,
    val concert_order: Int,
    val device_name: String
)

data class ConcertOrder(
    val id: Int,
    val user: Int,
    val total_price: String,
    val tickets_concert: List<ConcertTicket>
)

data class ConcertDetail(
    val id: Int,
    val title: String,
    val description: String,
    val concert_date: String?,
    val time: String,
    val place: ConcertPlace,
    val artist: String,
    val age_limit: Int
)

data class ConcertTicket(
    val id: String,
    val seats_id: List<Seat>,
    val concert_title: String,
    val concert_place: String,
    val concert_images: List<ConcertDetailImage>,
    val concert_date: String,
    val qr_code: String,
    val bar_code: String,
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
    val seats_id: List<Int?>,
    val qr_code: String,
    val bar_code: String,
    val user: Int
)



