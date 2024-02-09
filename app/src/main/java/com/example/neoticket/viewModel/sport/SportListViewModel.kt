package com.example.neoticket.viewModel.sport

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.SportData
import com.example.neoticket.model.Theater
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SportListViewModel : ViewModel() {
    private val _sportLiveData = MutableLiveData<List<SportData>?>()
    val sportLiveData: MutableLiveData<List<SportData>?> get() = _sportLiveData

    fun getSportList() {
        val apiInterface = RetrofitInstance.sportApi

        val call = apiInterface.getSportList()
        call.enqueue(object : Callback<List<SportData>> {
            override fun onResponse(call: Call<List<SportData>>, response: Response<List<SportData>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _sportLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<SportData>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun getSportListBySearch(search: String? = null, param: (List<SportData>) -> Unit) {
        val apiInterface = RetrofitInstance.sportApi

        val call = apiInterface.getSportList(search)
        call.enqueue(object : Callback<List<SportData>> {
            override fun onResponse(call: Call<List<SportData>>, response: Response<List<SportData>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _sportLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<SportData>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}