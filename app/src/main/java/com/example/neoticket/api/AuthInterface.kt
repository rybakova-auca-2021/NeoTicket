package com.example.neoticket.api

import com.example.neoticket.model.BankAccount
import com.example.neoticket.model.ConfirmCode
import com.example.neoticket.model.EditProfile
import com.example.neoticket.model.RegisterUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthInterface {
    @POST("authentication/register-or-login/")
    fun registerUser(
        @Body request: RegisterUser
    ) : Call<RegisterUser>

    @POST("authentication/confirm-code/")
    fun confirmCode(
        @Body request: ConfirmCode
    ) : Call<ConfirmCode>

    @PUT("authentication/profile/update/")
    fun editProfile(
        @Body request: EditProfile
    ) : Call<EditProfile>

    @GET("authentication/me/")
    fun myProfile(@Header("Authorization") token: String) : Call<EditProfile>

    @GET("authentication/bank-card/")
    fun myCard(@Header("Authorization") token: String) : Call<BankAccount>
}