package com.example.neoticket.viewModel.concerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.ConcertOrderRefund
import com.example.neoticket.model.ConcertOrderRefundResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConcertOrderRefundViewModel : ViewModel() {
    private val _orderConcertLiveData = MutableLiveData<ConcertOrderRefundResponse?>()
    val orderConcertLiveData: MutableLiveData<ConcertOrderRefundResponse?> get() = _orderConcertLiveData

    fun orderConcert(user: Int, movieOrder: Int) {
        val apiInterface = RetrofitInstance.concertApi
        val request = ConcertOrderRefund(user, movieOrder, "Kami")

        val call = apiInterface.concertOrderRefund(request)
        call.enqueue(object : Callback<ConcertOrderRefundResponse> {
            override fun onResponse(call: Call<ConcertOrderRefundResponse>, response: Response<ConcertOrderRefundResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderConcertLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ConcertOrderRefundResponse>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}