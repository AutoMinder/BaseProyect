package com.autominder.autominder.data.network.retrofit

import com.autominder.autominder.data.network.services.AutominderApi
import okhttp3.OkHttpClient
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
        .client(
            OkHttpClient()
                .newBuilder()
                .addInterceptor { chain ->
                    chain.proceed(
                        chain.request()
                            .newBuilder()
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                    )
                }
                .build()
        )
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getLoginService(): AutominderApi {
        return retrofit.create(AutominderApi::class.java)
    }

    //OwnCars segment
    fun getOwnCarsService(): AutominderApi{
        return retrofit.create(AutominderApi::class.java)
    }
}