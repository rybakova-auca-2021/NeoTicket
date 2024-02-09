package com.example.neoticket.viewModel.theater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.TheaterSection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TheaterSectionDetailViewModel : ViewModel() {
    private val _sectionLiveData = MutableLiveData<TheaterSection?>()
    val sectionLiveData: MutableLiveData<TheaterSection?> get() = _sectionLiveData

    fun getSectionDetail(id: Int) {
        val apiInterface = RetrofitInstance.theaterApi

        val call = apiInterface.getSectionDetail(id)
        call.enqueue(object : Callback<TheaterSection> {
            override fun onResponse(call: Call<TheaterSection>, response: Response<TheaterSection>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _sectionLiveData.value = body
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<TheaterSection>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}