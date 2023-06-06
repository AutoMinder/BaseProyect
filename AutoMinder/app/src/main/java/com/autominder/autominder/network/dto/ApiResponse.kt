package com.autominder.autominder.network.dto

sealed class ApiResponse<T>{
    data class Success<T>(val data: T): ApiResponse<T>()

    data class Error<T>(val message: String): ApiResponse<T>()

    data class ErrorWithMessage<T>(val message: String): ApiResponse<T>()
}

