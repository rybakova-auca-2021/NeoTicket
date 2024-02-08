package com.example.neoticket.viewModel.sport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.SportDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SportDetailViewModel : ViewModel() {
    private val _sportDetailLiveData = MutableLiveData<SportDetail?>()
    val sportDetailLiveData: MutableLiveData<SportDetail?> get() = _sportDetailLiveData

    fun getSportDetail(id: Int) {
        val apiInterface = RetrofitInstance.sportApi

        val call = apiInterface.getSportDetail(id)
        call.enqueue(object : Callback<SportDetail> {
            override fun onResponse(call: Call<SportDetail>, response: Response<SportDetail>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _sportDetailLiveData.value = body
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SportDetail>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}