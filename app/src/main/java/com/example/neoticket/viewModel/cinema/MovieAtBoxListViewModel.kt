package com.example.neoticket.viewModel.cinema

import DisplayableItem
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.Cinema
import com.example.neoticket.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieAtBoxListViewModel : ViewModel() {

    private val _moviesLiveData = MutableLiveData<List<Movie>?>()
    val moviesLiveData: MutableLiveData<List<Movie>?> get() = _moviesLiveData

    fun getMovies() {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getMoviesAtTheBox()
        call.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _moviesLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun getMoviesBySearch(search: String? = null, param: (List<Movie>) -> Unit) {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getMoviesAtTheBox(search)
        call.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _moviesLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }

    fun getMoviesByLocation(location: String? = null, param: (List<Movie>) -> Unit) {
        val apiInterface = RetrofitInstance.movieApi

        val call = apiInterface.getMoviesAtTheBox(null, location)
        call.enqueue(object : Callback<List<Movie>> {
            override fun onResponse(call: Call<List<Movie>>, response: Response<List<Movie>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    _moviesLiveData.value = body

                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Movie>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}
