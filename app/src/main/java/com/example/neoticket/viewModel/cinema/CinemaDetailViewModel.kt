package com.example.neoticket.viewModel.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.CinemaDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CinemaDetailViewModel : ViewModel() {
    private val _cinemaDetailLiveData = MutableLiveData<CinemaDetail?>()
    val cinemaDetailLiveData: MutableLiveData<CinemaDetail?> get() = _cinemaDetailLiveData

    fun getCinemaDetail(id: Int) {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getCinemaDetail(id = id)
        call.enqueue(object : Callback<CinemaDetail> {
            override fun onResponse(call: Call<CinemaDetail>, response: Response<CinemaDetail>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _cinemaDetailLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CinemaDetail>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}