package com.example.neoticket.viewModel.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.NotificationDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetNotificationByIdViewModel : ViewModel() {
    private val _notificationsLiveData = MutableLiveData<NotificationDetail?>()
    val notificationsLiveData: MutableLiveData<NotificationDetail?> get() = _notificationsLiveData

    fun getNotificationDetail(id: Int) {
        val apiInterface = RetrofitInstance.notificationApi
        val call = apiInterface.getNotificationById(id)
        call.enqueue(object : Callback<NotificationDetail> {
            override fun onResponse(call: Call<NotificationDetail>, response: Response<NotificationDetail>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _notificationsLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<NotificationDetail>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}