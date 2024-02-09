package com.example.neoticket.viewModel.theater

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.TheaterSection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TheaterSectionListViewModel : ViewModel() {
    private val _sectionListLiveData = MutableLiveData<List<TheaterSection>?>()
    val sectionListLiveData: MutableLiveData<List<TheaterSection>?> get() = _sectionListLiveData

    fun getSectionList() {
        val apiInterface = RetrofitInstance.theaterApi

        val call = apiInterface.getSection()
        call.enqueue(object : Callback<List<TheaterSection>> {
            override fun onResponse(call: Call<List<TheaterSection>>, response: Response<List<TheaterSection>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _sectionListLiveData.value = body
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<TheaterSection>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}