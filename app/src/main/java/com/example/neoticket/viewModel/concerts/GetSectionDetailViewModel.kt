package com.example.neoticket.viewModel.concerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.Section
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetSectionDetailViewModel : ViewModel() {
    private val _sectionLiveData = MutableLiveData<Section?>()
    val sectionLiveData: MutableLiveData<Section?> get() = _sectionLiveData

    fun getSectionDetail(id: Int) {
        val apiInterface = RetrofitInstance.concertApi

        val call = apiInterface.getSectionDetail(id)
        call.enqueue(object : Callback<Section> {
            override fun onResponse(call: Call<Section>, response: Response<Section>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _sectionLiveData.value = body
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Section>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}