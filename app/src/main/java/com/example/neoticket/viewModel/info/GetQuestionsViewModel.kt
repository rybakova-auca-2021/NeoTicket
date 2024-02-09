package com.example.neoticket.viewModel.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.Question
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetQuestionsViewModel : ViewModel() {
    private val _questionsLiveData = MutableLiveData<List<Question>?>()
    val questionsLiveData: MutableLiveData<List<Question>?> get() = _questionsLiveData

    fun getQuestions() {
        val apiInterface = RetrofitInstance.questionApi

        val call = apiInterface.getQuestions()
        call.enqueue(object : Callback<List<Question>> {
            override fun onResponse(call: Call<List<Question>>, response: Response<List<Question>>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _questionsLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Question>>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}