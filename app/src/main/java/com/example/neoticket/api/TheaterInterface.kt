package com.example.neoticket.api

import com.example.neoticket.model.Theater
import com.example.neoticket.model.TheaterDetail
import com.example.neoticket.model.TheaterOrder
import com.example.neoticket.model.TheaterOrderCheckout
import com.example.neoticket.model.TheaterOrderCheckoutResponse
import com.example.neoticket.model.TheaterOrderRefund
import com.example.neoticket.model.TheaterOrderRefundResponse
import com.example.neoticket.model.TheaterSection
import com.example.neoticket.model.TheaterTicket
import com.example.neoticket.model.TheaterTicketCreate
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TheaterInterface {
    @GET("theater/list/")
    fun getTheaters(
        @Query("search") search: String? = null,
        @Query("place_name") place_name: String? = null,
        @Query("location_name") location: String? = null
    ) : Call<List<Theater>>

    @GET("theater/detail/{id}/")
    fun getTheaterDetail(@Path("id") id: Int) : Call<TheaterDetail>

    @POST("theater/order/checkout/")
    fun theaterOrderCheckout(@Body request: TheaterOrderCheckout) : Call<TheaterOrderCheckoutResponse>

    @POST("theater/order/refund/")
    fun theaterOrderRefund(@Body request: TheaterOrderRefund) : Call<TheaterOrderRefundResponse>


    @GET("theater/order/{id}/")
    fun getTheaterData(
        @Path("id") id: Int
    ) : Call<TheaterOrder>

    @GET("theater/section/list/")
    fun getSection() : Call<List<TheaterSection>>

    @GET("theater/section/detail/{id}/")
    fun getSectionDetail(@Path("id") id: Int) : Call<TheaterSection>

    @POST("theater/ticket/create/")
    fun createTicket(
        @Body request: TheaterTicketCreate
    ) : Call<TheaterTicket>
}