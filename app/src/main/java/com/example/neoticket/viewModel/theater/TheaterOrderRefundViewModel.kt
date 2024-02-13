package com.example.neoticket.viewModel.theater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.TheaterOrderRefund
import com.example.neoticket.model.TheaterOrderRefundResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TheaterOrderRefundViewModel : ViewModel() {
    private val _orderConcertLiveData = MutableLiveData<TheaterOrderRefundResponse?>()
    val orderTheaterLiveData: MutableLiveData<TheaterOrderRefundResponse?> get() = _orderConcertLiveData

    fun orderTheater(user: Int, order: Int) {
        val apiInterface = RetrofitInstance.theaterApi
        val request = TheaterOrderRefund(user, order, "Kami")

        val call = apiInterface.theaterOrderRefund(request)
        call.enqueue(object : Callback<TheaterOrderRefundResponse> {
            override fun onResponse(call: Call<TheaterOrderRefundResponse>, response: Response<TheaterOrderRefundResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderConcertLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TheaterOrderRefundResponse>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}