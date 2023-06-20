package com.autominder.autominder.data.network.RepositoryCredentials

import android.util.Log
import androidx.datastore.preferences.protobuf.Api
import androidx.lifecycle.viewmodel.compose.viewModel
import com.autominder.autominder.data.DataStoreManager
import com.autominder.autominder.data.models_dummy.CarModel
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

    //Login function
    suspend fun login(email: String, password: String): ApiResponse<String> {

        Log.d("CredentialsRepository", "login: $email, $password") //Log the email and password

        return try { //Try to login
            val response: LoginResponse = api.login(LoginRequest(email, password)) //Get the response from the api
            return ApiResponse.Success(response.token) //Return the token
        } catch (e: HttpException) {//If there is an error
            if (e.code() == 400) {//If the error is 400
                return ApiResponse.ErrorWithMessage("Credenciales incorrectas, email or password") //Return an error with a message
            }
            return ApiResponse.Error(e) //Return an error
        } catch (e: IOException) { //If there is any other error
            return ApiResponse.Error(e)
        }
    }

    //Register function
    suspend fun register(email: String, password: String, name: String): ApiResponse<String> {
        return try {//Try to register
            val response: RegisterResponse = api.register(RegisterRequest(email, password, name)) //Get the response from the api
            return ApiResponse.Success(response.message) //Return the message
        } catch (e: HttpException) { //If there is an error
            if (e.code() == 400) { //If the error is 400
                return ApiResponse.ErrorWithMessage("El usuario que desea ingresar ya existe")
            }
            return ApiResponse.Error(e)
        } catch (e: IOException) {
            return ApiResponse.Error(e)
        }
    }

    //Save the user data
    suspend fun saveUserData(token: String) {
        userDataManager.saveUserData(//Save the user data
            token//Save the token
        )
    }


    //fun getUserData() = userDataManager.getUserData()

    //TODO(): Check function parameters and token sent to the API
    //Adding a new car function
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

        // Section to test saving vehicles

        //create a date val to get the current date
        val date = Date()

        //create a val to get the current date in string format



        return try {
            val response: CreateResponse = api.create( //Sending car parameters so it can be added to online database
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
            return ApiResponse.Success(response.id) //Return the id (will only happen once the car has been created successfully)
        } catch (e: HttpException) {
            if (e.code() == 400) {
             //TODO()
            }
            return ApiResponse.Error(e)
        } catch (e: IOException) {
            return ApiResponse.Error(e)
        }
    }

    //Get own cars function
    suspend fun ownCars(): ApiResponse<OwnResponse> {

        // Section to try getting vehicles
        try {
            val response = api.ownCars() //Get the response from the api

            if (response.isSuccessful) { //If the response is successful

                val carsResponse = response.body() //Get the body of the response
                val carsList = carsResponse?.cars //Get the list of cars

                //TODO(): Once function with CarInfoVM is complete, remove this next section
//                // Process the list of cars
                carsList?.forEach { car -> //For each car in the list
                    Log.d("I GOT THE CARS", car.name) //Log the name of the car (testing purposes)
                }

                return ApiResponse.Success(response.body()!!) //Return the body of the response
            }

            //If the response is not successful
            return ApiResponse.ErrorWithMessage("Error al obtener los autos")

        } //Section of error handling
        catch (e: HttpException) {
            if (e.code() == 400) { //Error produced by incorrect authorization credentials
                return ApiResponse.ErrorWithMessage("Credenciales incorrectas, email or password")
            }
            return ApiResponse.Error(e)
        }
        catch (e: IOException) { //Any other error
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

    suspend fun getCarById(id: String): CarModel {

        val cars = (ownCars() as ApiResponse.Success<OwnResponse>).data.cars

        val searchedCar = cars.find{car -> car.id == id}!!

        return searchedCar
    }
}