package com.example.neoticket.viewModel.concerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.Section
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetSectionListViewModel : ViewModel() {
    private val _sectionListLiveData = MutableLiveData<List<Section>?>()
    val sectionListLiveData: MutableLiveData<List<Section>?> get() = _sectionListLiveData

    fun getSectionList() {
        val apiInterface = RetrofitInstance.concertApi

        val call = apiInterface.getSection()
        call.enqueue(object : Callback<List<Section>> {
            override fun onResponse(call: Call<List<Section>>, response: Response<List<Section>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _sectionListLiveData.value = body
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Section>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}