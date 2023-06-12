package com.autominder.autominder.database.repository

import com.autominder.autominder.database.dao.CarDao
import com.autominder.autominder.database.models.CarModel

class CarRepository(private val carDao: CarDao) {
    suspend fun addCar(newCar: CarModel) = carDao.insertCar(newCar)
    suspend fun getMyCars(user_id: Long) = carDao.getMyCars(user_id)
}