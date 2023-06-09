package com.autominder.autominder.database.repository

import com.autominder.autominder.network.ApiResponse
import com.autominder.autominder.network.dto.login.LoginRequest
import com.autominder.autominder.network.dto.login.LoginResponse
import com.autominder.autominder.network.dto.register.RegisterRequest
import com.autominder.autominder.network.dto.register.RegisterResponse
import com.autominder.autominder.network.services.AutominderApi
import retrofit2.HttpException
import java.io.IOException

class CredentialsRepository(private val api: AutominderApi) {

    suspend fun login(email: String, password: String): ApiResponse<String> {
        return try {
            val response: LoginResponse = api.login(LoginRequest(email, password))
            return ApiResponse.Success(response.token)
        } catch (e: HttpException) {
            if (e.code() == 400) {
                return ApiResponse.ErrorWithMessage("Credenciales incorrectas, email or password")
            }
            return ApiResponse.Error(e)
        } catch (e: IOException) {
            return ApiResponse.Error(e)
        }
    }

    suspend fun register(email: String, password: String, name: String): ApiResponse<String> {
        return try {
            val response: RegisterResponse = api.register(RegisterRequest(email, password, name))
            return ApiResponse.Success(response.message)
        } catch (e: HttpException) {

            if (e.code() == 400) {
                return ApiResponse.ErrorWithMessage("El usuario que desea ingresar ya existe")
            }
            return ApiResponse.Error(e)
        } catch (e: IOException) {
            return ApiResponse.Error(e)
        }
    }





}