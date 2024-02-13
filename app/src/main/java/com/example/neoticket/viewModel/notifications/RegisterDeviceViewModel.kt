package com.example.neoticket.viewModel.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.NotificationRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterDeviceViewModel : ViewModel() {
    private val _registerDeviceLiveData = MutableLiveData<NotificationRequest?>()
    val registerDeviceLiveData: MutableLiveData<NotificationRequest?> get() = _registerDeviceLiveData

    fun registerDevice(name: String, token: String) {
        val apiInterface = RetrofitInstance.notificationApi
        val request = NotificationRequest(name, "android", token)

        val call = apiInterface.sendNotification(request)
        call.enqueue(object : Callback<NotificationRequest> {
            override fun onResponse(call: Call<NotificationRequest>, response: Response<NotificationRequest>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _registerDeviceLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<NotificationRequest>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}