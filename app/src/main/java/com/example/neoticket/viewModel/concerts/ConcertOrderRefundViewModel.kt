package com.example.neoticket.viewModel.concerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.ConcertOrderCheckout
import com.example.neoticket.model.ConcertOrderCheckoutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConcertOrderRefundViewModel : ViewModel() {
    private val _orderConcertLiveData = MutableLiveData<ConcertOrderCheckoutResponse?>()
    val orderConcertLiveData: MutableLiveData<ConcertOrderCheckoutResponse?> get() = _orderConcertLiveData

    fun orderConcert(user: Int, movieOrder: Int) {
        val apiInterface = RetrofitInstance.concertApi
        val request = ConcertOrderCheckout(user, movieOrder)

        val call = apiInterface.concertOrderRefund(request)
        call.enqueue(object : Callback<ConcertOrderCheckoutResponse> {
            override fun onResponse(call: Call<ConcertOrderCheckoutResponse>, response: Response<ConcertOrderCheckoutResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderConcertLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ConcertOrderCheckoutResponse>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}