package com.example.neoticket.viewModel.sport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.SportOrderCheckout
import com.example.neoticket.model.SportOrderCheckoutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SportOrderRefundViewModel : ViewModel() {
    private val _orderSportLiveData = MutableLiveData<SportOrderCheckoutResponse?>()
    val orderSportLiveData: MutableLiveData<SportOrderCheckoutResponse?> get() = _orderSportLiveData

    fun orderSport(user: Int, order: Int) {
        val apiInterface = RetrofitInstance.sportApi
        val request = SportOrderCheckout(user, order)

        val call = apiInterface.sportOrderRefund(request)
        call.enqueue(object : Callback<SportOrderCheckoutResponse> {
            override fun onResponse(call: Call<SportOrderCheckoutResponse>, response: Response<SportOrderCheckoutResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderSportLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SportOrderCheckoutResponse>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}