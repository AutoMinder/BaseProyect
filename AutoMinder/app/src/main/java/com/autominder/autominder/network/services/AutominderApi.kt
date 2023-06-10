package com.autominder.autominder.network.services

import com.autominder.autominder.network.dto.create.CreateRequest
import com.autominder.autominder.network.dto.create.CreateResponse
import com.autominder.autominder.network.dto.login.LoginRequest
import com.autominder.autominder.network.dto.login.LoginResponse
import com.autominder.autominder.network.dto.register.RegisterRequest
import com.autominder.autominder.network.dto.register.RegisterResponse
import com.autominder.autominder.network.dto.visibility.VisibilityResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AutominderApi {
    @POST("/auth/signin")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    @POST("/auth/signup")
    suspend fun register(@Body credentials: RegisterRequest): RegisterResponse



    // TODO() Desarrollar pedir informacion de usuario
    @GET("/auth/whoami")
    suspend fun whoami(): String




    @POST("/post")
    suspend fun create(@Body credentials: CreateRequest): CreateResponse

    // TODO() Desarrollar pedir carros propios

    @GET("/post/own")
    suspend fun ownCars(): String




    @PATCH("/post/visibility/{post_id}")
    suspend fun visibility(@Path("post_id") post_id: String): VisibilityResponse







}