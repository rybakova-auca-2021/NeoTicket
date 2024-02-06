package com.example.neoticket.viewModel.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.MovieOrder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetMovieOrderDetailViewModel : ViewModel() {
    private val _orderMovieLiveData = MutableLiveData<MovieOrder?>()
    val orderMovieLiveData: MutableLiveData<MovieOrder?> get() = _orderMovieLiveData

    fun getMovieOrderDetail(id: Int) {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getMovieOrderDetail(id)
        call.enqueue(object : Callback<MovieOrder> {
            override fun onResponse(call: Call<MovieOrder>, response: Response<MovieOrder>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderMovieLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieOrder>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}