package com.example.neoticket.viewModel

import androidx.lifecycle.ViewModel
import com.example.neoticket.Utils.Util
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.IsUserRegistered
import com.example.neoticket.model.RegisterUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckUserViewModel : ViewModel() {
    fun checkUser(
        email: String,
    ) {
        val request = RegisterUser(email)
        val apiInterface = RetrofitInstance.authApi

        val call = apiInterface.checkUser(request)
        call.enqueue(object : Callback<IsUserRegistered> {
            override fun onResponse(
                call: Call<IsUserRegistered>,
                response: Response<IsUserRegistered>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Util.isUserRegistered = responseBody.email
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<IsUserRegistered>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }
}