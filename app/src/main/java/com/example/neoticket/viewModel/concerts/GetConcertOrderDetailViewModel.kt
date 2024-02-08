package com.example.neoticket.viewModel.concerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.ConcertOrder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetConcertOrderDetailViewModel : ViewModel() {
    private val _orderConcertLiveData = MutableLiveData<ConcertOrder?>()
    val orderConcertLiveData: MutableLiveData<ConcertOrder?> get() = _orderConcertLiveData

    fun getConcertOrderDetail(id: Int) {
        val apiInterface = RetrofitInstance.concertApi

        val call = apiInterface.getConcertData(id)
        call.enqueue(object : Callback<ConcertOrder> {
            override fun onResponse(call: Call<ConcertOrder>, response: Response<ConcertOrder>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderConcertLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ConcertOrder>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}