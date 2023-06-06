package com.autominder.autominder.network.services

import com.autominder.autominder.network.dto.login.LoginRequest
import com.autominder.autominder.network.dto.login.LoginResponse
import com.autominder.autominder.network.dto.register.RegisterRequest
import com.autominder.autominder.network.dto.register.RegisterResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AutominderApi {
    @POST("/auth/signin")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    @POST("/auth/signup")
    suspend fun register(@Body credentials: RegisterRequest): RegisterResponse

}