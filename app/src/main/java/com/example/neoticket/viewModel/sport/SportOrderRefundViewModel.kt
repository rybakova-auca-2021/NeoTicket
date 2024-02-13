package com.example.neoticket.viewModel.sport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.SportOrderRefund
import com.example.neoticket.model.SportOrderRefundResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SportOrderRefundViewModel : ViewModel() {
    private val _orderSportLiveData = MutableLiveData<SportOrderRefundResponse?>()
    val orderSportLiveData: MutableLiveData<SportOrderRefundResponse?> get() = _orderSportLiveData

    fun orderSport(user: Int, order: Int) {
        val apiInterface = RetrofitInstance.sportApi
        val request = SportOrderRefund(user, order, "Kami")

        val call = apiInterface.sportOrderRefund(request)
        call.enqueue(object : Callback<SportOrderRefundResponse> {
            override fun onResponse(call: Call<SportOrderRefundResponse>, response: Response<SportOrderRefundResponse>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _orderSportLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SportOrderRefundResponse>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}