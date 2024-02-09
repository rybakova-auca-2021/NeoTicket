package com.example.neoticket.viewModel.info

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.QuestionAnswer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetQuestionAnswerViewModel : ViewModel() {
    private val _questionsLiveData = MutableLiveData<QuestionAnswer?>()
    val questionsLiveData: MutableLiveData<QuestionAnswer?> get() = _questionsLiveData

    fun getQuestionAnswer(id: Int) {
        val apiInterface = RetrofitInstance.questionApi

        val call = apiInterface.getQuestionAnswers(id)
        call.enqueue(object : Callback<QuestionAnswer> {
            override fun onResponse(call: Call<QuestionAnswer>, response: Response<QuestionAnswer>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _questionsLiveData.value = body
                    }
                } else {
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<QuestionAnswer>, t: Throwable) {
                println("Request failed: ${t.message}")
            }
        })
    }
}