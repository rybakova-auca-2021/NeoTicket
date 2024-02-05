package com.example.neoticket.viewModel.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.ShowTime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetShowTimeViewModel : ViewModel() {
    private val _showTimeLiveData = MutableLiveData<List<ShowTime>?>()
    val showTimeLiveData: MutableLiveData<List<ShowTime>?> get() = _showTimeLiveData

    fun getMoviesShowTime() {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getShowTime()
        call.enqueue(object : Callback<List<ShowTime>> {
            override fun onResponse(call: Call<List<ShowTime>>, response: Response<List<ShowTime>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _showTimeLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ShowTime>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun getShowTimeByMovie(movie: String) {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getShowTime(movie)
        call.enqueue(object : Callback<List<ShowTime>> {
            override fun onResponse(call: Call<List<ShowTime>>, response: Response<List<ShowTime>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _showTimeLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<ShowTime>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}