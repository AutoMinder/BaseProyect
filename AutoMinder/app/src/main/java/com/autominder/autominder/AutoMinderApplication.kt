package com.autominder.autominder

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

import com.autominder.autominder.addcar.data.AddCarRepository
import com.autominder.autominder.addcar.data.carsDummy
import com.autominder.autominder.addcar.data.brands
import com.autominder.autominder.addcar.data.models

import com.autominder.autominder.carinfo.data.CarMaintenanceRepository
import com.autominder.autominder.carinfo.data.dummyCarMaintenanceData
import com.autominder.autominder.database.AutominderDatabase
import com.autominder.autominder.database.repository.CarRepository
import com.autominder.autominder.database.repository.UserRepository

import com.autominder.autominder.myCars.data.MyCarsRepository
import com.autominder.autominder.myCars.data.myCarsdummy
import com.autominder.autominder.network.RepositoryCredentials.CredentialsRepository
import com.autominder.autominder.network.retrofit.RetrofitInstance
import com.autominder.autominder.principalMenu.data.AlertsRepository
import com.autominder.autominder.principalMenu.data.dummyAlerts

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
    val CarMaintenanceRepository: CarMaintenanceRepository by lazy {
//        CarMaintenanceRepository(dummyCarMaintenanceData) -   CHEQUEAR ESTE CAMPO, DA ERROR
        TODO()
    }

    //  DATABASE INSTANCES
    private val database: AutominderDatabase by lazy {
        AutominderDatabase.newInstance(this)
    }

    val userRepository: UserRepository by lazy{
        UserRepository(database.ownerDao())
    }

    val carRepository: CarRepository by lazy{
        CarRepository(database.carDao())
    }


    //RETROFIT

    private val prefs: SharedPreferences by lazy{
        getSharedPreferences(USER_TOKEN, Context.MODE_PRIVATE)
    }

    private fun getAPIService() = with(RetrofitInstance){
        setToken(getToken())
        getLoginService()
    }

    fun getToken():String = prefs.getString(USER_TOKEN, "")!!

    val credentialsRepository: CredentialsRepository by lazy {
        CredentialsRepository(getAPIService())
    }

    fun saveAuthToken(token: String){
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    companion object{
        const val USER_TOKEN = "user_token"
    }
}