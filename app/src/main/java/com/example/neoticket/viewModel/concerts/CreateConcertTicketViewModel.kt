package com.example.neoticket.viewModel.concerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.ConcertTicket
import com.example.neoticket.model.ConcertTicketCreate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateConcertTicketViewModel : ViewModel() {
    private val _createTicketLiveData = MutableLiveData<ConcertTicket?>()
    val createTicketLiveData: MutableLiveData<ConcertTicket?> get() = _createTicketLiveData

    fun createTicket(showTime: Int, seats: List<Int?>, user: Int) {
        val apiInterface = RetrofitInstance.concertApi
        val request = ConcertTicketCreate(showTime, seats, user)

        val call = apiInterface.createTicket(request)
        call.enqueue(object : Callback<ConcertTicket> {
            override fun onResponse(
                call: Call<ConcertTicket>,
                response: Response<ConcertTicket>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _createTicketLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ConcertTicket>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}