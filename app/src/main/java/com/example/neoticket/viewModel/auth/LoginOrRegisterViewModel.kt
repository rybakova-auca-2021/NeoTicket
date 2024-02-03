package com.example.neoticket.viewModel.auth

import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.RegisterUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginOrRegisterViewModel : ViewModel() {
    fun register(
        email: String,
        onSuccess: () -> Unit,
        onError:() -> Unit
    ) {
        val apiInterface = RetrofitInstance.authApi

        val request = RegisterUser(email)
        val call = apiInterface.registerUser(request)
        call.enqueue(object : Callback<RegisterUser> {
            override fun onResponse(
                call: Call<RegisterUser>,
                response: Response<RegisterUser>
            ) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                    val responseBody = response.body()
                } else {
                    onError.invoke()
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<RegisterUser>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }
}