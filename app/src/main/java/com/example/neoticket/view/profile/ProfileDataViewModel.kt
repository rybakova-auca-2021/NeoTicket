package com.example.neoticket.view.profile


import androidx.lifecycle.ViewModel
import com.example.neoticket.Utils.Util
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.BankAccount
import com.example.neoticket.model.EditProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileDataViewModel : ViewModel() {
    fun getProfile(
        onSuccess: (EditProfile) -> Unit,
    ) {
        val apiInterface = RetrofitInstance.authApi

        val token = Util.token
        val authHeader = "Bearer $token"

        val call = apiInterface.myProfile(authHeader)
        call.enqueue(object : Callback<EditProfile> {
            override fun onResponse(
                call: Call<EditProfile>,
                response: Response<EditProfile>
            ) {
                if (response.isSuccessful) {
                    val profile = response.body()
                    if (profile != null) {
                        onSuccess.invoke(profile)
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<EditProfile>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }

    fun getCard(
        onSuccess: (List<BankAccount>) -> Unit,
    ) {
        val apiInterface = RetrofitInstance.authApi

        val token = Util.token
        val authHeader = "Bearer $token"

        val call = apiInterface.myCard(authHeader)
        call.enqueue(object : Callback<List<BankAccount>> {
            override fun onResponse(
                call: Call<List<BankAccount>>,
                response: Response<List<BankAccount>>
            ) {
                if (response.isSuccessful) {
                    val card = response.body()
                    if (card != null) {
                        onSuccess.invoke(card)
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<BankAccount>>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }

    fun updateProfile(
        name: String,
        email: String,
        phone: String,
        onSuccess: () -> Unit
    ) {
        val apiInterface = RetrofitInstance.authApi

        val request = EditProfile(name, email,phone)
        val token = Util.token
        val authHeader = "Bearer $token"

        val call = apiInterface.editProfile(authHeader, request)
        call.enqueue(object : Callback<EditProfile> {
            override fun onResponse(
                call: Call<EditProfile>,
                response: Response<EditProfile>
            ) {
                if (response.isSuccessful) {
                    val profile = response.body()
                    onSuccess.invoke()
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<EditProfile>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }
}