package com.example.neoticket.viewModel.notifications

import androidx.lifecycle.ViewModel
import com.example.neoticket.Utils.Util
import com.example.neoticket.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeleteNotificationByIdViewModel : ViewModel() {
    private val apiInterface = RetrofitInstance.notificationApi
    fun deleteNotifications(
        id: Int,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {

        val token = Util.token
        val authHeader = "Bearer $token"
        apiInterface.deleteNotification(id, authHeader)
            .enqueue(getCallback(onSuccess, onError))
    }

    private fun getCallback(onSuccess: () -> Unit, onError: () -> Unit): Callback<Unit> {
        return object : Callback<Unit> {
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        onSuccess.invoke()
                    }
                } else {
                    onError.invoke()
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                onError.invoke()
                println("Request failed: ${t.message}")
            }
        }
    }
}