package com.example.neoticket.viewModel

import androidx.lifecycle.ViewModel
import com.example.neoticket.Utils.Util
import com.example.neoticket.api.RetrofitInstance
import com.example.neoticket.model.ConfirmCode
import com.example.neoticket.model.ConfirmCodeResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CheckCodeViewModel : ViewModel() {
    fun checkCode(
        code: String,
        onSuccess: () -> Unit,
        onError:() -> Unit
    ) {
        val request = ConfirmCode(code)
        val apiInterface = RetrofitInstance.authApi

        val call = apiInterface.confirmCode(request)
        call.enqueue(object : Callback<ConfirmCodeResponse> {
            override fun onResponse(
                call: Call<ConfirmCodeResponse>,
                response: Response<ConfirmCodeResponse>
            ) {
                if (response.isSuccessful) {
                    onSuccess.invoke()
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Util.token = responseBody.access
                    }
                } else {
                    onError.invoke()
                    println("Request failed with status code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ConfirmCodeResponse>, t: Throwable) {
                println("Request failed: ${t.message}")                }
        })
    }
}