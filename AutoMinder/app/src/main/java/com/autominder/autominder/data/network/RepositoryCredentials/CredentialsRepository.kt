package com.autominder.autominder.data.network.RepositoryCredentials

import android.util.Log
import androidx.datastore.preferences.protobuf.Api
import com.autominder.autominder.data.DataStoreManager
import com.autominder.autominder.data.network.ApiResponse
import com.autominder.autominder.data.network.dto.create.CreateRequest
import com.autominder.autominder.data.network.dto.create.CreateResponse
import com.autominder.autominder.data.network.dto.login.LoginRequest
import com.autominder.autominder.data.network.dto.login.LoginResponse
import com.autominder.autominder.data.network.dto.ownCars.OwnResponse
import com.autominder.autominder.data.network.dto.register.RegisterRequest
import com.autominder.autominder.data.network.dto.register.RegisterResponse
import com.autominder.autominder.data.network.dto.update.UpdateRequest
import com.autominder.autominder.data.network.dto.update.UpdateResponse
import com.autominder.autominder.data.network.services.AutominderApi
import retrofit2.HttpException
import java.io.IOException
import java.util.Date

class CredentialsRepository(
    private val api: AutominderApi,
    private val userDataManager: DataStoreManager
) {

    suspend fun login(email: String, password: String): ApiResponse<String> {
        Log.d("CredentialsRepository", "login: $email, $password")
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

    suspend fun saveUserData(token: String) {
        userDataManager.saveUserData(
            token
        )
    }


    //fun getUserData() = userDataManager.getUserData()

    suspend fun addCar(
        car_name: String,
        model: String,
        brand: String,
        year: String,
        kilometers: String,
        kilometersDate: String? = null,
        lastMaintenance: String,
        lastOilChange: String,
        lastCoolantChange: String,
        mayorTuning: String?,
        minorTuning: String?,
        errorRecord: List<String>? = null,
        vin: String = "321321",
    ): ApiResponse<String> {

        //create a date val to get the current date
        val date = Date()

        //create a val to get the current date in string format



        return try {
            val response: CreateResponse = api.create(
                token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NDhhODM2ZjJmMTI5ZGIxNzUzMjU4ODEiLCJpYXQiOjE2ODY4MDA5MDEsImV4cCI6MTY4OTM5MjkwMX0.e8CxQV6BhnzxNGFrqQvYRlzmV8tz7KsZ9aCLCIEcSuI",
                CreateRequest(
                    vin,
                    car_name,
                    brand,
                    model,
                    year,
                    kilometers,
                    lastMaintenance,
                    mayorTuning,
                    minorTuning,
                    lastOilChange,
                    lastCoolantChange,
                )
            )
            return ApiResponse.Success(response.id)
        } catch (e: HttpException) {

            if (e.code() == 400) {
             //TODO()
            }
            return ApiResponse.Error(e)
        } catch (e: IOException) {
            return ApiResponse.Error(e)
        }
    }
    

    suspend fun ownCars(): ApiResponse<OwnResponse> {

        try {
            val response = api.ownCars()

            if (response.isSuccessful) {
                val carsResponse = response.body()
                val carsList = carsResponse?.cars

                // Process the list of cars
                carsList?.forEach { car ->
                    Log.d("I GOT THE CARS", car.name)
                }

                return ApiResponse.Success(response.body()!!)
            }

            return ApiResponse.ErrorWithMessage("Error al obtener los autos")

        } catch (e: HttpException) {
            if (e.code() == 400) {
                return ApiResponse.ErrorWithMessage("Credenciales incorrectas, email or password")
            }
            return ApiResponse.Error(e)
        } catch (e: IOException) {
            return ApiResponse.Error(e)
        }
    }

    suspend fun updateCar(
        car_name: String,
        kilometers: String,
        lastMaintenance: String,
        mayorTuning: String,
        minorTuning: String,
        lastOilChange: String,
        lastCoolantChange: String
    ): ApiResponse<String> {
        return try {
            val response: UpdateResponse = api.update(
                token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiI2NDhhODM2ZjJmMTI5ZGIxNzUzMjU4ODEiLCJpYXQiOjE2ODY4MDA5MDEsImV4cCI6MTY4OTM5MjkwMX0.e8CxQV6BhnzxNGFrqQvYRlzmV8tz7KsZ9aCLCIEcSuI",
                UpdateRequest(
                car_name,
                kilometers,
                lastMaintenance,
                mayorTuning,
                minorTuning,
                lastOilChange,
                lastCoolantChange
                )
            )
            return ApiResponse.Success(response.message)
        } catch (e: HttpException) {
            if (e.code() == 404) {
                return ApiResponse.ErrorWithMessage("Post no encontrado")
            }
            return ApiResponse.Error(e)
        } catch (e: IOException) {
            return ApiResponse.Error(e)
        }
    }

}