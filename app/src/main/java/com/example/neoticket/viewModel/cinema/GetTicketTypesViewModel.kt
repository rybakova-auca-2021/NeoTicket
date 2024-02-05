package com.example.neoticket.viewModel.cinema

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.MovieTicketCreate
import com.example.neoticket.model.TicketType
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetTicketTypesViewModel : ViewModel() {
    private val _ticketTypeLiveData = MutableLiveData<List<TicketType>?>()
    val ticketTypeLiveData: MutableLiveData<List<TicketType>?> get() = _ticketTypeLiveData
    fun getTicketTypes() {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getTicketType()
        call.enqueue(object : Callback<List<TicketType>> {
            override fun onResponse(
                call: Call<List<TicketType>>,
                response: Response<List<TicketType>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _ticketTypeLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<TicketType>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}