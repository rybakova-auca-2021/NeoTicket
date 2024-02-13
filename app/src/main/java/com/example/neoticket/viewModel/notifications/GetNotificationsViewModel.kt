package com.example.neoticket.viewModel.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.Utils.Util
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.Notification
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetNotificationsViewModel : ViewModel() {
    private val _notificationsLiveData = MutableLiveData<List<Notification>?>()
    val notificationsLiveData: MutableLiveData<List<Notification>?> get() = _notificationsLiveData

    fun getNotifications() {
        val apiInterface = RetrofitInstance.notificationApi
        val token = Util.token
        val authHeader = "Bearer $token"
        val call = apiInterface.getNotification(authHeader)
        call.enqueue(object : Callback<List<Notification>> {
            override fun onResponse(call: Call<List<Notification>>, response: Response<List<Notification>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _notificationsLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Notification>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}