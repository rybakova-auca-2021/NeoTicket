package com.example.neoticket.room

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.Entity
import androidx.room.PrimaryKey

@Parcelize
@Entity (tableName = "TicketDB")
data class TicketData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val showTime: Int,
    val rowNumber: Int,
    val seatNumber: Int,
    val ticketType: Int,
    val price: String
) : Parcelable

@Parcelize
@Entity (tableName = "ConcertTicketDB")
data class ConcertTicketData(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val showTime: Int,
    val rowNumber: Int,
    val seatNumber: Int,
) : Parcelable