package com.example.neoticket.viewModel.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.Cinema
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CinemaListViewModel : ViewModel() {
    private val _cinemasLiveData = MutableLiveData<List<Cinema>?>()
    val cinemasLiveData: MutableLiveData<List<Cinema>?> get() = _cinemasLiveData

    fun getCinemas() {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getCinemaList()
        call.enqueue(object : Callback<List<Cinema>> {
            override fun onResponse(call: Call<List<Cinema>>, response: Response<List<Cinema>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _cinemasLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Cinema>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun getCinemasBySearch(search: String? = null, param: (List<Cinema>) -> Unit) {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getCinemaList(search)
        call.enqueue(object : Callback<List<Cinema>> {
            override fun onResponse(call: Call<List<Cinema>>, response: Response<List<Cinema>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _cinemasLiveData.value = body
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Cinema>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}