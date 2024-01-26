package com.example.neoticket.model

data class ConfirmCode(
    val code: String
)
data class ConfirmCodeResponse(
    val message: String,
    val user_id: Int,
    val access: String,
    val refresh: String
)