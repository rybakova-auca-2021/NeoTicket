package com.example.neoticket.viewModel.concerts

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.Concert
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ConcertListViewModel : ViewModel() {

    private val _concertsLiveData = MutableLiveData<List<Concert>?>()
    val concertsLiveData: MutableLiveData<List<Concert>?> get() = _concertsLiveData

    fun getConcerts() {
        val apiInterface = RetrofitInstance.concertApi

        val call = apiInterface.getConcerts()
        call.enqueue(object : Callback<List<Concert>> {
            override fun onResponse(call: Call<List<Concert>>, response: Response<List<Concert>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _concertsLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Concert>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun getConcertsBySearch(search: String? = null, param: (List<Concert>) -> Unit) {
        val apiInterface = RetrofitInstance.concertApi

        val call = apiInterface.getConcerts(search)
        call.enqueue(object : Callback<List<Concert>> {
            override fun onResponse(call: Call<List<Concert>>, response: Response<List<Concert>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _concertsLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Concert>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun getConcertsByPlace(place: String? = null) {
        val apiInterface = RetrofitInstance.concertApi

        val call = apiInterface.getConcerts(null, place)
        call.enqueue(object : Callback<List<Concert>> {
            override fun onResponse(call: Call<List<Concert>>, response: Response<List<Concert>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _concertsLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Concert>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun getConcertsByLocation(location: String? = null, param: (List<Concert>) -> Unit) {
        val apiInterface = RetrofitInstance.concertApi

        val call = apiInterface.getConcerts(null, null, location)
        call.enqueue(object : Callback<List<Concert>> {
            override fun onResponse(call: Call<List<Concert>>, response: Response<List<Concert>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _concertsLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Concert>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}
