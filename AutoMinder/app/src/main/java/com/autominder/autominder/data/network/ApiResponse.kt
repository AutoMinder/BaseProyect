package com.autominder.autominder.data.network

import com.autominder.autominder.data.network.dto.ownCars.OwnResponse
import java.lang.Exception

//Sealed class for handling api response
sealed class ApiResponse<T>{

    /*
    * This class is used to handle api response.
    * T is the type of data that will be returned.
    * This class is sealed class, which means it can only be extended from this file.
     */

    //Success response
    data class Success<T>(val data: T): ApiResponse<T>()

    //Error response
    data class Error<T>(val exception: Exception): ApiResponse<T>()

    //Error response with message
    data class ErrorWithMessage<T>(val message: String): ApiResponse<T>()

}

