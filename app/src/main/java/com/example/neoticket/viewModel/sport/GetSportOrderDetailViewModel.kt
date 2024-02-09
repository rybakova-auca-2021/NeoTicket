package com.example.neoticket.viewModel.sport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.SportOrder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetSportOrderDetailViewModel : ViewModel() {
    private val _orderSportLiveData = MutableLiveData<SportOrder?>()
    val orderSportLiveData: MutableLiveData<SportOrder?> get() = _orderSportLiveData

    fun getSportOrderDetail(id: Int) {
        val apiInterface = RetrofitInstance.sportApi

        val call = apiInterface.getSportOrderData(id)
        call.enqueue(object : Callback<SportOrder> {
            override fun onResponse(call: Call<SportOrder>, response: Response<SportOrder>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderSportLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SportOrder>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}