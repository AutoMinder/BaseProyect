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
import com.autominder.autominder.database.repository.OwnerAndCarRepository
import com.autominder.autominder.database.repository.OwnerRepository

import com.autominder.autominder.myCars.data.MyCarsRepository
import com.autominder.autominder.myCars.data.myCarsdummy
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
        AddCarRepository(carsDummy, brands, models)

    }
    val CarMaintenanceRepository: CarMaintenanceRepository by lazy {
        CarMaintenanceRepository(dummyCarMaintenanceData)
    }

    //  DATABASE INSTANCE
    private val database: AutominderDatabase by lazy {
        AutominderDatabase.newInstance(this)
    }

    val ownerRepository: OwnerRepository by lazy{
        OwnerRepository(database.ownerDao())
    }

    val carRepository: CarRepository by lazy{
        CarRepository(database.carDao())
    }

    val ownerAndCarRepository: OwnerAndCarRepository by lazy{
        OwnerAndCarRepository(database.ownerAndCarDao())
    }






}