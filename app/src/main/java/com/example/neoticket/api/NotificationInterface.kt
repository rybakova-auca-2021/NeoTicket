package com.example.neoticket.api

import com.example.neoticket.model.Notification
import com.example.neoticket.model.NotificationDetail
import com.example.neoticket.model.NotificationRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface NotificationInterface {
    @POST("notification/device/register/")
    fun sendNotification(
        @Body request: NotificationRequest
    ): Call<NotificationRequest>

    @GET("notification/notification/")
    fun getNotification(@Header("Authorization") token: String): Call<List<Notification>>

    @DELETE("notification/notification/delete/{id}/")
    fun deleteNotification(@Path("id") id: Int, @Header("Authorization") token: String): Call<Unit>

    @DELETE("notification/notification/delete/all/")
    fun deleteAllNotifications(@Header("Authorization") token: String): Call<Unit>

    @GET("notification/{id}/")
    fun getNotificationById(@Path("id") id: Int): Call<NotificationDetail>
}