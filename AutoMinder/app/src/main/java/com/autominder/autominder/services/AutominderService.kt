package com.autominder.autominder.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AutominderService {

    private val service: AutominderApi = Retrofit.Builder()
        .baseUrl("https://autominder.tech/api")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AutominderApi::class.java)
}