package com.example.neoticket.api

import com.example.neoticket.model.SportData
import com.example.neoticket.model.SportOrder
import com.example.neoticket.model.SportOrderCheckout
import com.example.neoticket.model.SportOrderCheckoutResponse
import com.example.neoticket.model.SportSection
import com.example.neoticket.model.SportTicket
import com.example.neoticket.model.SportTicketCreate
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

interface SportInterface {
    @GET("sport/list/")
    fun getSportList() : Call<SportData>

    @POST("sport/order/checkout/")
    fun sportOrderCheckout(@Body request: SportOrderCheckout) : Call<SportOrderCheckoutResponse>

    @GET("sport/order/{id}/")
    fun getSportOrderData(
        @Path("id") id: Int
    ) : Call<SportOrder>

    @GET("sport/section/list/")
    fun getSportSection() : Call<SportSection>

    @POST("sport/ticket/create/")
    fun createSportTicket(
        @Body request: SportTicketCreate
    ) : Call<SportTicket>
}