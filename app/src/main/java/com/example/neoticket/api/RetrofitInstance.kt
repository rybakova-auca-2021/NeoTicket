package com.example.neoticket.api

import com.example.neoticket.api.Constant.Constant.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val authApi by lazy {
            retrofit.create(AuthInterface::class.java)
        }

        val concertApi by lazy {
            retrofit.create(ConcertInterface::class.java)
        }

        val movieApi by lazy {
            retrofit.create(MovieInterface::class.java)
        }

        val theaterApi by lazy {
            retrofit.create(TheaterInterface::class.java)
        }

        val sportApi by lazy {
            retrofit.create(SportInterface::class.java)
        }
        val questionApi by lazy {
            retrofit.create(QuestionsInterface::class.java)
        }
    }
}