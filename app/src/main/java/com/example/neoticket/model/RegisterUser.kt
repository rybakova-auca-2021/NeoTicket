package com.example.neoticket.model

data class RegisterUser(
    val email: String
)

data class EditProfile(
    val username: String,
    val email: String,
    val phone: String
)

data class BankAccount(
    val account_number: String,
    val account_holder: String,
    val balance: String
)
