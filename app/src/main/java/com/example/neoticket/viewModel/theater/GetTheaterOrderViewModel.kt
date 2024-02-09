package com.example.neoticket.viewModel.theater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.TheaterOrder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetTheaterOrderViewModel : ViewModel() {
    private val _orderTheaterLiveData = MutableLiveData<TheaterOrder?>()
    val orderTheaterLiveData: MutableLiveData<TheaterOrder?> get() = _orderTheaterLiveData

    fun getTheaterOrderDetail(id: Int) {
        val apiInterface = RetrofitInstance.theaterApi

        val call = apiInterface.getTheaterData(id)
        call.enqueue(object : Callback<TheaterOrder> {
            override fun onResponse(call: Call<TheaterOrder>, response: Response<TheaterOrder>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderTheaterLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TheaterOrder>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}