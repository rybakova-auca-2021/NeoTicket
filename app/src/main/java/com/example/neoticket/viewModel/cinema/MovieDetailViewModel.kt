package com.example.neoticket.viewModel.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.MovieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDetailViewModel : ViewModel() {
    private val _movieDetailLiveData = MutableLiveData<MovieDetail?>()
    val movieDetailLiveData: MutableLiveData<MovieDetail?> get() = _movieDetailLiveData

    fun getCinemaDetail(id: Int) {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getMovieDetail(id = id)
        call.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _movieDetailLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}