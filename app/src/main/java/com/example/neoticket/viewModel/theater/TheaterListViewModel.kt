package com.example.neoticket.viewModel.theater

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.Concert
import com.example.neoticket.model.Theater
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TheaterListViewModel : ViewModel() {
    private val _theaterLiveData = MutableLiveData<List<Theater>?>()
    val theaterLiveData: MutableLiveData<List<Theater>?> get() = _theaterLiveData

    fun getTheaters() {
        val apiInterface = RetrofitInstance.theaterApi

        val call = apiInterface.getTheaters()
        call.enqueue(object : Callback<List<Theater>> {
            override fun onResponse(call: Call<List<Theater>>, response: Response<List<Theater>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _theaterLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Theater>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun getTheatersBySearch(search: String? = null, param: (List<Theater>) -> Unit) {
        val apiInterface = RetrofitInstance.theaterApi

        val call = apiInterface.getTheaters(search)
        call.enqueue(object : Callback<List<Theater>> {
            override fun onResponse(call: Call<List<Theater>>, response: Response<List<Theater>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _theaterLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Theater>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun getTheatersByPlace(place: String? = null) {
        val apiInterface = RetrofitInstance.theaterApi

        val call = apiInterface.getTheaters(null, place)
        call.enqueue(object : Callback<List<Theater>> {
            override fun onResponse(call: Call<List<Theater>>, response: Response<List<Theater>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _theaterLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Theater>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}