package com.autominder.autominder.data.network.retrofit

import com.autominder.autominder.data.network.services.AutominderApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://autominder.tech/api/"

object RetrofitInstance {

    private var token = ""

    fun setToken(token: String) {
        RetrofitInstance.token = token
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getLoginService(): AutominderApi {
        return retrofit.create(AutominderApi::class.java)
    }




}