package com.example.neoticket.api

import com.example.neoticket.model.Movie
import com.example.neoticket.model.MovieDetail
import com.example.neoticket.model.ShowTime
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieInterface {
    @GET("movie/detail/{id}/")
    fun getMovieDetail(@Path("id") id: Int) : Call<MovieDetail>

    @GET("movie/list/at-the-box-office/")
    fun getMoviesAtTheBox() : Call<Movie>

    @GET("movie/list/coming-soon-to-cinema/")
    fun getComingSoonMovies() : Call<Movie>

    @GET("movie/show-times/")
    fun getShowTime() : Call<ShowTime>

}