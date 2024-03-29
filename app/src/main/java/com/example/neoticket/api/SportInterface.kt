package com.example.neoticket.api

import com.example.neoticket.model.SportData
import com.example.neoticket.model.SportDetail
import com.example.neoticket.model.SportOrder
import com.example.neoticket.model.SportOrderCheckout
import com.example.neoticket.model.SportOrderCheckoutResponse
import com.example.neoticket.model.SportOrderRefund
import com.example.neoticket.model.SportOrderRefundResponse
import com.example.neoticket.model.SportSection
import com.example.neoticket.model.SportTicket
import com.example.neoticket.model.SportTicketCreate
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
import retrofit2.http.Query

interface SportInterface {
    @GET("sport/list/")
    fun getSportList(@Query("search") search: String? = null, @Query("place_name") place_name: String? = null) : Call<List<SportData>>

    @GET("sport/detail/{id}/")
    fun getSportDetail(@Path("id") id: Int) : Call<SportDetail>

    @POST("sport/order/checkout/")
    fun sportOrderCheckout(@Body request: SportOrderCheckout) : Call<SportOrderCheckoutResponse>

    @POST("sport/order/refund/")
    fun sportOrderRefund(@Body request: SportOrderRefund) : Call<SportOrderRefundResponse>

    @GET("sport/order/{id}/")
    fun getSportOrderData(
        @Path("id") id: Int
    ) : Call<SportOrder>

    @GET("sport/section/detail/{id}/")
    fun getSportSectionDetail(@Path("id") id: Int) : Call<SportSection>

    @GET("sport/section/list/")
    fun getSportSection() : Call<List<SportSection>>

    @POST("sport/ticket/create/")
    fun createSportTicket(
        @Body request: SportTicketCreate
    ) : Call<SportTicket>
}