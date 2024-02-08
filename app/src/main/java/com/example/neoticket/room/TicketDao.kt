package com.example.neoticket.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TicketDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addTicket(ticket: TicketData)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addConcertTicket(ticket: ConcertTicketData)

    @Query("DELETE FROM TicketDB WHERE rowNumber = :rowNumber AND seatNumber = :seatNumber AND ticketType = :ticketType")
    suspend fun deleteTicket(rowNumber: Int, seatNumber: Int, ticketType: Int)

    @Query("SELECT * FROM TicketDB")
    suspend fun getAllTickets(): List<TicketData>

    @Query("SELECT * FROM ConcertTicketDB")
    suspend fun getAllConcertTickets(): List<ConcertTicketData>

    @Query("DELETE FROM ConcertTicketDB WHERE rowNumber = :rowNumber AND seatNumber = :seatNumber")
    suspend fun deleteConcertTicket(rowNumber: Int, seatNumber: Int)

    @Query("SELECT * FROM TicketDB WHERE id = :ticketId")
    suspend fun getTicketById(ticketId: Int): TicketData?
}