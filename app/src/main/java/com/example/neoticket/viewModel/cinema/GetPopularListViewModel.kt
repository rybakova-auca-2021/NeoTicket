package com.example.neoticket.viewModel.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.Popular
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetPopularListViewModel : ViewModel() {
    private val _popularLiveData = MutableLiveData<List<Popular>?>()
    val moviesLiveData: MutableLiveData<List<Popular>?> get() = _popularLiveData

    fun getPopularList() {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getPopularList()
        call.enqueue(object : Callback<List<Popular>> {
            override fun onResponse(call: Call<List<Popular>>, response: Response<List<Popular>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _popularLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Popular>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}