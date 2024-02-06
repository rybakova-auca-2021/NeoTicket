package com.example.neoticket.room

import android.app.Application

class MyApplication : Application() {
    val database by lazy { AppDatabase.getDatabase(this) }

}