package com.autominder.autominder.database.repository

import com.autominder.autominder.network.dto.ApiResponse
import com.autominder.autominder.network.dto.login.LoginRequest
import com.autominder.autominder.network.dto.register.RegisterRequest
import com.autominder.autominder.network.services.AutominderApi
import retrofit2.HttpException
import java.io.IOException

class CredentialsRepository(private val api: AutominderApi) {

    suspend fun login(email: String, password: String): ApiResponse<String>{
        try{
            val response = api.login(LoginRequest(email, password))
            return ApiResponse.Success(response.token)

        }catch (e: HttpException){
            if (e.code() == 401){
                return ApiResponse.Error("Invalid credentials, email or password")
            }
            return ApiResponse.Error(e.message())
        }
        catch (e: IOException){
            return ApiResponse.Error(e.toString())
        }
    }

    suspend fun register(user: String, email: String, password: String): ApiResponse<String>{
        try{
            val response = api.register(RegisterRequest(user, email, password))
            return ApiResponse.Success("")

        }catch (e: HttpException){
            if (e.code() == 401){
                return ApiResponse.Error("Invalid new credentials")
            }
            return ApiResponse.Error(e.message())
        }
        catch (e: IOException){
            return ApiResponse.Error(e.toString())
        }
    }





}