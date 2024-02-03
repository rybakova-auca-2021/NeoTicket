package com.example.neoticket.api

import com.example.neoticket.model.Theater
import com.example.neoticket.model.TheaterOrder
import com.example.neoticket.model.TheaterOrderCheckout
import com.example.neoticket.model.TheaterOrderCheckoutResponse
import com.example.neoticket.model.TheaterSection
import com.example.neoticket.model.TheaterTicket
import com.example.neoticket.model.TheaterTicketCreate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TheaterInterface {
    @GET("theater/list/")
    fun getTheaters() : Call<List<Theater>>

    @POST("theater/order/checkout/")
    fun theaterOrderCheckout(@Body request: TheaterOrderCheckout) : Call<TheaterOrderCheckoutResponse>

    @GET("theater/order/{id}/")
    fun getTheaterData(
        @Path("id") id: Int
    ) : Call<TheaterOrder>

    @GET("theater/section/list/")
    fun getSection() : Call<TheaterSection>

    @POST("theater/ticket/create/")
    fun createTicket(
        @Body request: TheaterTicketCreate
    ) : Call<TheaterTicket>
}