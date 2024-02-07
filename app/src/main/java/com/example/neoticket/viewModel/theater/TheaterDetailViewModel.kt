package com.example.neoticket.viewModel.theater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.Utils.Util
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.TheaterDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TheaterDetailViewModel : ViewModel() {
    private val _theaterDetailLiveData = MutableLiveData<TheaterDetail?>()
    val theaterDetailLiveData: MutableLiveData<TheaterDetail?> get() = _theaterDetailLiveData

    fun getTheaterDetail(id: Int) {
        val apiInterface = RetrofitInstance.theaterApi

        val call = apiInterface.getTheaterDetail(id)
        call.enqueue(object : Callback<TheaterDetail> {
            override fun onResponse(call: Call<TheaterDetail>, response: Response<TheaterDetail>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _theaterDetailLiveData.value = body
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TheaterDetail>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}