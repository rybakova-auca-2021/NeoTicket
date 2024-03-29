package com.example.neoticket.api

import com.example.neoticket.model.Cinema
import com.example.neoticket.model.CinemaDetail
import com.example.neoticket.model.Movie
import com.example.neoticket.model.MovieDetail
import com.example.neoticket.model.MovieOrder
import com.example.neoticket.model.MovieOrderCheckout
import com.example.neoticket.model.MovieOrderCheckoutResponse
import com.example.neoticket.model.MovieOrderRefund
import com.example.neoticket.model.MovieOrderRefundResponse
import com.example.neoticket.model.MovieTicket
import com.example.neoticket.model.MovieTicketCreate
import com.example.neoticket.model.Popular
import com.example.neoticket.model.ShowTime
import com.example.neoticket.model.StartTimeDetail
import com.example.neoticket.model.Ticket
import com.example.neoticket.model.TicketType
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {

    @GET("movie/cinema/detail/{id}")
    fun getCinemaDetail(
        @Path("id") id: Int
    ) : Call<CinemaDetail>

    @GET("movie/cinema/list/")
    fun getCinemaList(@Query("search") search: String? = null) : Call<List<Cinema>>

    @GET("movie/detail/{id}/")
    fun getMovieDetail(@Path("id") id: Int) : Call<MovieDetail>

    @GET("movie/list/at-the-box-office/")
    fun getMoviesAtTheBox(
        @Query("search") search: String? = null,
        @Query("location_name") location: String? = null
    ) : Call<List<Movie>>

    @GET("movie/list/coming-soon-to-cinema/")
    fun getComingSoonMovies(@Query("search") search: String? = null) : Call<List<Movie>>

    @GET("movie/show-times/")
    fun getShowTime(@Query("movie_id") movie: String? = null) : Call<List<ShowTime>>

    @GET("movie/show-times/detail/{id}/")
    fun getMovieShowTimeDetail(@Path("id") id: Int) : Call<StartTimeDetail>

    @POST("movie/order/checkout/")
    fun orderMovie(@Body request: MovieOrderCheckout) : Call<MovieOrderCheckoutResponse>

    @POST("movie/order/refund/")
    fun refundMovie(@Body request: MovieOrderRefund) : Call<MovieOrderRefundResponse>

    @GET("movie/order/{id}/")
    fun getMovieOrderDetail(@Path("id") id: Int) : Call<MovieOrder>

    @GET("movie/ticket-type/list/")
    fun getTicketType() : Call<List<TicketType>>

    @POST("movie/ticket/create/")
    fun createTicket(@Body request: MovieTicketCreate) : Call<MovieTicket>

    @GET("popular-events/")
    fun getPopularList(@Query("location_name") location: String? = null) : Call<List<Popular>>

    @GET("my-order/")
    fun getMyTickets(@Header("Authorization") token: String) : Call<List<Ticket>>
}