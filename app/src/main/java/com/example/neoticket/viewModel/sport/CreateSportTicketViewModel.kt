package com.example.neoticket.viewModel.sport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.SportTicket
import com.example.neoticket.model.SportTicketCreate
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateSportTicketViewModel : ViewModel() {
    private val _createTicketLiveData = MutableLiveData<SportTicket?>()
    val createTicketLiveData: MutableLiveData<SportTicket?> get() = _createTicketLiveData

    fun createTicket(showTime: Int, seats: List<Int?>, user: Int, qr: String, bar: String) {
        val apiInterface = RetrofitInstance.sportApi
        val request = SportTicketCreate(showTime, seats, qr, bar, user)

        val call = apiInterface.createSportTicket(request)
        call.enqueue(object : Callback<SportTicket> {
            override fun onResponse(
                call: Call<SportTicket>,
                response: Response<SportTicket>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _createTicketLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SportTicket>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}