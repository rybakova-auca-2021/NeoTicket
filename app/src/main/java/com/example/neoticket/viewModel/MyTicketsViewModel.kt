package com.example.neoticket.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.Utils.Util
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.Ticket
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyTicketsViewModel : ViewModel() {
    private val _ticketLiveData = MutableLiveData<List<Ticket>?>()
    val ticketLiveData: MutableLiveData<List<Ticket>?> get() = _ticketLiveData

    fun getTicketsList() {
        val apiInterface = RetrofitInstance.movieApi

        val token = Util.token
        val authHeader = "Bearer $token"

        val call = apiInterface.getMyTickets(authHeader)
        call.enqueue(object : Callback<List<Ticket>> {
            override fun onResponse(call: Call<List<Ticket>>, response: Response<List<Ticket>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _ticketLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Ticket>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

}