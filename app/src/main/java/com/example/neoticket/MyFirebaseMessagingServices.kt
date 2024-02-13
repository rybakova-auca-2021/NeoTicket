package com.example.neoticket

import android.content.ContentValues
import android.util.Log
import com.example.neoticket.Utils.Util
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(ContentValues.TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(ContentValues.TAG, "Message data payload: ${remoteMessage.data}")
        }

        remoteMessage.notification?.let {
            Log.d(ContentValues.TAG, "Message Notification Body: ${it.body}")
            Log.d(ContentValues.TAG, "Message Notification Title: ${it.title}")
            Util.title = it.title
            Util.body = it.body
        }
    }

    override fun onNewToken(token: String) {
        Log.d(ContentValues.TAG, "Refreshed token: $token")
        Util.FCMToken = token
    }
}