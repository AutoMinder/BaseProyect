package com.autominder.autominder.network.services

import com.autominder.autominder.network.models.response.PostListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AutominderApi {

    @GET("posts")
    suspend fun getPosts(
    ): PostListResponse

}