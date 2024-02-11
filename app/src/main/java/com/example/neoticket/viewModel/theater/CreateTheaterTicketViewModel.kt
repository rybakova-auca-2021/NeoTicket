package com.example.neoticket.viewModel.theater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.TheaterTicket
import com.example.neoticket.model.TheaterTicketCreate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateTheaterTicketViewModel : ViewModel() {
    private val _createTicketLiveData = MutableLiveData<TheaterTicket?>()
    val createTicketLiveData: MutableLiveData<TheaterTicket?> get() = _createTicketLiveData

    fun createTicket(showTime: Int, seats: List<Int?>, user: Int, qr: String, bar: String) {
        val apiInterface = RetrofitInstance.theaterApi
        val request = TheaterTicketCreate(showTime, seats, qr , bar, user)

        val call = apiInterface.createTicket(request)
        call.enqueue(object : Callback<TheaterTicket> {
            override fun onResponse(
                call: Call<TheaterTicket>,
                response: Response<TheaterTicket>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _createTicketLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TheaterTicket>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}