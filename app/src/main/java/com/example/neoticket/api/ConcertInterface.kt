package com.example.neoticket.api

import com.example.neoticket.model.Concert
import com.example.neoticket.model.ConcertDetail
import com.example.neoticket.model.ConcertOrder
import com.example.neoticket.model.ConcertOrderCheckout
import com.example.neoticket.model.ConcertOrderCheckoutResponse
import com.example.neoticket.model.ConcertTicket
import com.example.neoticket.model.ConcertTicketCreate
import com.example.neoticket.model.Section
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ConcertInterface {
    @GET("concert/list/")
    fun getConcerts(@Query("search") search: String? = null, @Query("place_name") place_name: String? = null) : Call<List<Concert>>

    @GET("concert/detail/{id}/")
    fun getConcertDetail(
        @Path("id") id: Int
    ) : Call<ConcertDetail>

    @POST("concert/order/checkout/")
    fun concertOrderCheckout(@Body request: ConcertOrderCheckout) : Call<ConcertOrderCheckoutResponse>

    @GET("concert/order/{id}/")
    fun getConcertData(
        @Path("id") id: Int
    ) : Call<ConcertOrder>

    @GET("concert/section/list/")
    fun getSection() : Call<List<Section>>

    @GET("concert/section/detail/{id}/")
    fun getSectionDetail(@Path("id") id: Int) : Call<Section>

    @POST("concert/ticket/create/")
    fun createTicket(
        @Body request: ConcertTicketCreate
    ) : Call<ConcertTicket>
}