package com.autominder.autominder.database.repository

import com.autominder.autominder.database.dao.CarDao
import com.autominder.autominder.database.models.CarModel

class CarRepository(private val carDao: CarDao) {
    suspend fun getCars() = carDao.getAllCars()

    suspend fun addCar(newCar: CarModel) = carDao.insertCar(newCar)

    suspend fun getMyCars(id: Long) = carDao.getMyCars(id)
}