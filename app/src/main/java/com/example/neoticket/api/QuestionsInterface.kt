package com.example.neoticket.api

import com.example.neoticket.model.Question
import com.example.neoticket.model.QuestionAnswer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface QuestionsInterface {

    @GET("authentication/question/list/")
    fun getQuestions() : Call<List<Question>>

    @GET("authentication/question-answer/{id}/")
    fun getQuestionAnswers(
        @Path("id") id: Int
    ) : Call<QuestionAnswer>
}