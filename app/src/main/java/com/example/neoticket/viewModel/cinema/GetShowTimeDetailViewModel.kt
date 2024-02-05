package com.example.neoticket.viewModel.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.StartTimeDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetShowTimeDetailViewModel : ViewModel() {
    private val _showTimeDetailLiveData = MutableLiveData<StartTimeDetail?>()
    val showTimeDetailLiveData: MutableLiveData<StartTimeDetail?> get() = _showTimeDetailLiveData

    fun getMoviesShowTime(id: Int) {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getMovieShowTimeDetail(id)
        call.enqueue(object : Callback<StartTimeDetail> {
            override fun onResponse(call: Call<StartTimeDetail>, response: Response<StartTimeDetail>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _showTimeDetailLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StartTimeDetail>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}