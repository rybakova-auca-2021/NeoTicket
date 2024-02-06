package com.example.neoticket.viewModel.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.MovieOrderCheckout
import com.example.neoticket.model.MovieOrderCheckoutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderMovieDetailViewModel : ViewModel() {
    private val _orderMovieLiveData = MutableLiveData<MovieOrderCheckoutResponse?>()
    val orderMovieLiveData: MutableLiveData<MovieOrderCheckoutResponse?> get() = _orderMovieLiveData

    fun getMoviesShowTime(user: Int, movieOrder: Int) {
        val apiInterface = RetrofitInstance.movieApi
        val request = MovieOrderCheckout(user, movieOrder)

        val call = apiInterface.orderMovie(request)
        call.enqueue(object : Callback<MovieOrderCheckoutResponse> {
            override fun onResponse(call: Call<MovieOrderCheckoutResponse>, response: Response<MovieOrderCheckoutResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderMovieLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieOrderCheckoutResponse>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}