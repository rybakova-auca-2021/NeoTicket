package com.example.neoticket.viewModel.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.MovieTicket
import com.example.neoticket.model.MovieTicketCreate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateTicketViewModel : ViewModel() {
    private val _createTicketLiveData = MutableLiveData<MovieTicket?>()
    val createTicketLiveData: MutableLiveData<MovieTicket?> get() = _createTicketLiveData

    fun createTicket(showTime: Int, seats: List<Int?>, user: Int, type: Int) {
        val apiInterface = RetrofitInstance.movieApi
        val request = MovieTicketCreate(showTime, seats, user, type)

        val call = apiInterface.createTicket(request)
        call.enqueue(object : Callback<MovieTicket> {
            override fun onResponse(
                call: Call<MovieTicket>,
                response: Response<MovieTicket>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _createTicketLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<MovieTicket>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}