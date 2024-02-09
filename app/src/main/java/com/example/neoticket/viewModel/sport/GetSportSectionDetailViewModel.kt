package com.example.neoticket.viewModel.sport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.SportSection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetSportSectionDetailViewModel : ViewModel() {
    private val _sectionLiveData = MutableLiveData<SportSection?>()
    val sectionLiveData: MutableLiveData<SportSection?> get() = _sectionLiveData

    fun getSectionDetail(id: Int) {
        val apiInterface = RetrofitInstance.sportApi

        val call = apiInterface.getSportSectionDetail(id)
        call.enqueue(object : Callback<SportSection> {
            override fun onResponse(call: Call<SportSection>, response: Response<SportSection>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _sectionLiveData.value = body
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SportSection>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}