package com.example.neoticket.viewModel.theater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.TheaterOrderCheckout
import com.example.neoticket.model.TheaterOrderCheckoutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckoutTheaterOrderViewModel : ViewModel() {
    private val _orderConcertLiveData = MutableLiveData<TheaterOrderCheckoutResponse?>()
    val orderTheaterLiveData: MutableLiveData<TheaterOrderCheckoutResponse?> get() = _orderConcertLiveData

    fun orderTheater(user: Int, order: Int) {
        val apiInterface = RetrofitInstance.theaterApi
        val request = TheaterOrderCheckout(user, order)

        val call = apiInterface.theaterOrderCheckout(request)
        call.enqueue(object : Callback<TheaterOrderCheckoutResponse> {
            override fun onResponse(call: Call<TheaterOrderCheckoutResponse>, response: Response<TheaterOrderCheckoutResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderConcertLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TheaterOrderCheckoutResponse>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}