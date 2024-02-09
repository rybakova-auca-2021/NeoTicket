package com.example.neoticket.viewModel.sport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.SportSection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetSportSectionListViewModel : ViewModel() {
    private val _sectionListLiveData = MutableLiveData<List<SportSection>?>()
    val sectionListLiveData: MutableLiveData<List<SportSection>?> get() = _sectionListLiveData

    fun getSectionList() {
        val apiInterface = RetrofitInstance.sportApi

        val call = apiInterface.getSportSection()
        call.enqueue(object : Callback<List<SportSection>> {
            override fun onResponse(call: Call<List<SportSection>>, response: Response<List<SportSection>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _sectionListLiveData.value = body
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<SportSection>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}