package com.autominder.autominder.data.network.retrofit

import com.autominder.autominder.data.network.services.AutominderApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header

// Base URL for the API
const val BASE_URL = "https://autominder.tech/api/"

// Retrofit instance
object RetrofitInstance {

    private var token = "" // Token for the API

    // Set the token for the API
    fun setToken(token: String) {
        RetrofitInstance.token = token // Set the token
    }

    // Build the Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL) // Set the base URL
        .client( // Add the token to the header of the request
            OkHttpClient() // Create a new OkHttpClient
                .newBuilder() // Create a new builder
                .addInterceptor { chain -> // Add the token to the header
                    chain.proceed( // Proceed with the request
                        chain.request() // Get the request
                            .newBuilder()
                            .addHeader("Authorization", "Bearer $token") // Add the token to the header
                            .build() // Build the request
                    )
                }
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create()) // Add the Gson converter
        .build() // Build the Retrofit instance


    // Get the login service
    fun getLoginService(): AutominderApi {
        return retrofit.create(AutominderApi::class.java)
    }

    // Get the own cars service
    fun getOwnCarsService(): AutominderApi{
        return retrofit.create(AutominderApi::class.java)
    }

    //Create car service
    fun createCarService(): AutominderApi{
        return retrofit.create(AutominderApi::class.java)
    }

    //Update car info service
    fun updateCarService(): AutominderApi{
        return retrofit.create(AutominderApi::class.java)
    }

    /**
     * "Who am I" service will return the information of the user that is currently logged in
     */
    fun whoAmIService(): AutominderApi {
        return retrofit.create(AutominderApi::class.java)
    }
}