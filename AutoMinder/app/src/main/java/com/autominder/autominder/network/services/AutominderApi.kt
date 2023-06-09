package com.autominder.autominder.network.services

import com.autominder.autominder.network.dto.create.CreateRequest
import com.autominder.autominder.network.dto.create.CreateResponse
import com.autominder.autominder.network.dto.login.LoginRequest
import com.autominder.autominder.network.dto.login.LoginResponse
import com.autominder.autominder.network.dto.register.RegisterRequest
import com.autominder.autominder.network.dto.register.RegisterResponse

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query

interface AutominderApi {
    @POST("/auth/signin")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    @POST("/auth/signup")
    suspend fun register(@Body credentials: RegisterRequest): RegisterResponse

    @POST("/post")
    suspend fun create(@Body credentials: CreateRequest): CreateResponse




    @GET("/auth/whoami")
    suspend fun whoami(): String

    @GET("/post/own")
    suspend fun ownCars(): String

    @GET("/post")
    suspend fun allCars(): String

    @GET("/post/{post_id}")
    suspend fun getCarPostById(@Query("id") id: String): String

    @GET("/post/user/{user_id}")
    suspend fun getCarPostByUserId(@Query("id") id: String): String





    @PATCH("/post/save/{post_id}")
    suspend fun save(@Query("id") id: String): String

    @PATCH("/post/visibility/{post_id}")
    suspend fun visibility(@Query("id") id: String): String







}