package com.autominder.autominder.data.network.services

import com.autominder.autominder.data.network.dto.create.CreateRequest
import com.autominder.autominder.data.network.dto.create.CreateResponse
import com.autominder.autominder.data.network.dto.login.LoginRequest
import com.autominder.autominder.data.network.dto.login.LoginResponse
import com.autominder.autominder.data.network.dto.ownCars.OwnCarsListResponse
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



    @GET("auth/whoami")
    suspend fun whoami(): WhoamiResponse

    //Create car service
    @POST("post")
    suspend fun create(@Body credentials: CreateRequest): CreateResponse

    //Update car info service
    @POST("post/update/{post_id}")
    suspend fun update(
        @Path("post_id") post_id: String,
        @Body credentials: UpdateRequest
    ): UpdateResponse

    //OwnCarsService
    @GET("post/own")
    suspend fun ownCars(): Response<OwnResponse>

    @GET("post/own")
    suspend fun getOwnCars(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): OwnCarsListResponse

    @PATCH("post/visibility/{post_id}")
    suspend fun visibility(@Path("post_id") post_id: String): VisibilityResponse


}