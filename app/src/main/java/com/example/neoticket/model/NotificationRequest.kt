package com.example.neoticket.model

data class NotificationRequest(
    val name : String,
    val device_type: String,
    val registration_token: String
)


data class Notification(
    val id: Int,
    val title: String,
    val body: String,
    val created_date: String
)

data class NotificationDetail(
    val id: Int,
    val title: String,
    val body: String,
    val created_date: String
)