package com.autominder.autominder.network.interceptor

import com.google.android.gms.cast.framework.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptorImpl @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor{

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
//        var accessToken = sessionManager.()

        val builder = request.newBuilder()
//        builder.addHeader("Authorization", "Bearer ${token}")
        return chain.proceed(builder.build())
    }
}