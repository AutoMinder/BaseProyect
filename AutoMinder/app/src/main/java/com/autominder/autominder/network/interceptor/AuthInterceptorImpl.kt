package com.autominder.autominder.network.interceptor


import okhttp3.Interceptor
import okhttp3.Response

abstract class AuthInterceptorImpl(): Interceptor{

//    override fun intercept(chain: Interceptor.Chain): Response {
//        val request = chain.request()
//        var accessToken = sessionManager.()
//
//        val builder = request.newBuilder()
//        builder.addHeader("Authorization", "Bearer ${token}")
//        return chain.proceed(builder.build())
//    }
}