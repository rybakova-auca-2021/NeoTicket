package com.example.neoticket.viewModel.concerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.ConcertDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConcertDetailViewModel : ViewModel() {
    private val _concertDetailLiveData = MutableLiveData<ConcertDetail?>()
    val concertDetailLiveData: MutableLiveData<ConcertDetail?> get() = _concertDetailLiveData

    fun getConcertDetail(id: Int) {
        val apiInterface = RetrofitInstance.concertApi

        val call = apiInterface.getConcertDetail(id)
        call.enqueue(object : Callback<ConcertDetail> {
            override fun onResponse(call: Call<ConcertDetail>, response: Response<ConcertDetail>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _concertDetailLiveData.value = body
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ConcertDetail>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}