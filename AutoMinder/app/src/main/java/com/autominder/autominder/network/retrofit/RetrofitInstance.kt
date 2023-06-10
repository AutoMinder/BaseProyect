package com.autominder.autominder.network.retrofit

import com.autominder.autominder.network.services.AutominderApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "http://autominder.tech/api"

object RetrofitInstance {

    private var token = ""

    fun setToken(token: String) {
        this.token = token
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getLoginService(): AutominderApi{
        return retrofit.create(AutominderApi::class.java)
    }




}