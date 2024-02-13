package com.example.neoticket.viewModel.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.MovieOrderCheckout
import com.example.neoticket.model.MovieOrderCheckoutResponse
import com.example.neoticket.model.MovieOrderRefund
import com.example.neoticket.model.MovieOrderRefundResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RefundMovieViewModel : ViewModel() {
    private val _orderMovieLiveData = MutableLiveData<MovieOrderRefundResponse?>()
    val orderMovieLiveData: MutableLiveData<MovieOrderRefundResponse?> get() = _orderMovieLiveData

    fun orderMovie(user: Int, movieOrder: Int) {
        val apiInterface = RetrofitInstance.movieApi
        val request = MovieOrderRefund(user, movieOrder, "Kami")

        val call = apiInterface.refundMovie(request)
        call.enqueue(object : Callback<MovieOrderRefundResponse> {
            override fun onResponse(call: Call<MovieOrderRefundResponse>, response: Response<MovieOrderRefundResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderMovieLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieOrderRefundResponse>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}