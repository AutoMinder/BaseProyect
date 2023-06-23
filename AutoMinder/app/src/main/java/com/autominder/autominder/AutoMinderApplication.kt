package com.autominder.autominder

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.autominder.autominder.data.DataStoreManager

import com.autominder.autominder.ui.addcar.data.AddCarRepository
import com.autominder.autominder.ui.addcar.data.brands
import com.autominder.autominder.ui.addcar.data.models

import com.autominder.autominder.data.database.AutominderDatabase
import com.autominder.autominder.data.database.repository.CarRepository
import com.autominder.autominder.data.database.repository.UserRepository

import com.autominder.autominder.ui.myCars.data.MyCarsRepository
import com.autominder.autominder.ui.myCars.data.myCarsdummy
import com.autominder.autominder.data.network.RepositoryCredentials.CredentialsRepository
import com.autominder.autominder.data.network.retrofit.RetrofitInstance
import com.autominder.autominder.ui.principalMenu.data.AlertsRepository
import com.autominder.autominder.ui.principalMenu.data.dummyAlerts

class AutoMinderApplication : Application() {

    val alertsRepository: AlertsRepository by lazy {
        AlertsRepository(dummyAlerts)
    }

    val myCarsRepository: MyCarsRepository by lazy {
        MyCarsRepository(myCarsdummy)
    }

    val addCarRepository: AddCarRepository by lazy {
        AddCarRepository(myCarsdummy, brands, models)

    }

    /*
    *   DATABASE SECTION
     */
    private val database: AutominderDatabase by lazy {
        AutominderDatabase.newInstance(this)
    }

    val userRepository: UserRepository by lazy{
        UserRepository(database.ownerDao())
    }

    val carRepository: CarRepository by lazy{
        CarRepository(database.carDao())
    }


    /*
    *   RETROFIT SECTION
     */
    private val prefs: SharedPreferences by lazy{ //SharedPreferences es una clase de Android que permite guardar datos en el dispositivo
        getSharedPreferences("Retrofit", Context.MODE_PRIVATE) //MODE_PRIVATE es el modo de acceso a los datos, en este caso solo la app puede acceder a los datos
    }

    private fun getAPIService() = with(RetrofitInstance){ //with es una funcion de Kotlin que permite llamar a una funcion de un objeto sin tener que escribir el nombre del objeto
        setToken(getToken()) //setToken es una funcion de RetrofitInstance que permite guardar el token en la clase RetrofitInstance
        getLoginService() //getLoginService es una funcion de RetrofitInstance que permite obtener el servicio de login
        getOwnCarsService() //getOwnCarsService es una funcion de RetrofitInstance que permite obtener el servicio de los autos del usuario
        createCarService() //createCarService es una funcion de RetrofitInstance que permite obtener el servicio de crear un auto
        updateCarService() //updateCarService es una funcion de RetrofitInstance que permite obtener el servicio de actualizar un auto
    }

    //getToken es una funcion que permite obtener el token guardado en el dispositivo
    fun getToken():String = prefs.getString(USER_TOKEN, "")!!

    val credentialsRepository: CredentialsRepository by lazy {//CredentialsRepository es una clase que permite obtener los servicios de login y de los autos del usuario
        CredentialsRepository(getAPIService(), DataStoreManager(this)) //DataStoreManager es una clase que permite guardar datos en el dispositivo
    }

    //saveAuthToken es una funcion que permite guardar el token en el dispositivo
    fun saveAuthToken(token: String){
        val editor = prefs.edit() //editor es una variable que permite editar los datos guardados en el dispositivo
        editor.putString(USER_TOKEN, token)
        editor.apply()
        RetrofitInstance.setToken(token) //setToken es una funcion de RetrofitInstance que permite guardar el token en la clase RetrofitInstance
    }

    //USER_TOKEN es una constante que permite acceder al token guardado en el dispositivo
    companion object{
        const val USER_TOKEN = "user_token"
    }
}