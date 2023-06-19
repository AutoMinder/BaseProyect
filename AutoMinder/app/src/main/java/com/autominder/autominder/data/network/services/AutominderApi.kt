package com.autominder.autominder.data.network.services

import com.autominder.autominder.data.network.dto.create.CreateRequest
import com.autominder.autominder.data.network.dto.create.CreateResponse
import com.autominder.autominder.data.network.dto.login.LoginRequest
import com.autominder.autominder.data.network.dto.login.LoginResponse
import com.autominder.autominder.data.network.dto.ownCars.OwnResponse
import com.autominder.autominder.data.network.dto.register.RegisterRequest
import com.autominder.autominder.data.network.dto.register.RegisterResponse
import com.autominder.autominder.data.network.dto.update.UpdateRequest
import com.autominder.autominder.data.network.dto.update.UpdateResponse
import com.autominder.autominder.data.network.dto.visibility.VisibilityResponse
import com.autominder.autominder.data.network.dto.whoami.WhoamiResponse

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AutominderApi {
    @POST("auth/signin")
    suspend fun login(@Body credentials: LoginRequest): LoginResponse

    @POST("auth/signup")
    suspend fun register(@Body credentials: RegisterRequest): RegisterResponse


    // TODO() Desarrollar pedir informacion de usuario
    @GET("auth/whoami")
    suspend fun whoami(
        @Header("Authorization") token: String,
    ): WhoamiResponse

    //TODO(): Revisar enrutado para inclusión de token dinamicamente
    @POST("post")
    suspend fun create(
        @Header("Authorization") token: String,
        @Body credentials: CreateRequest
    ): CreateResponse

    @POST("post/update/{post_id}")
    suspend fun update(
        @Header("Authorization") token: String,
        @Body credentials: UpdateRequest
    ): UpdateResponse

    // TODO() Desarrollar pedir carros propios

    //OwnCarsService
    @GET("post/own")
    suspend fun ownCars(): Response<OwnResponse> //TODO(): Considerar cambiar a OwnResponse


    @PATCH("post/visibility/{post_id}")
    suspend fun visibility(@Path("post_id") post_id: String): VisibilityResponse


}