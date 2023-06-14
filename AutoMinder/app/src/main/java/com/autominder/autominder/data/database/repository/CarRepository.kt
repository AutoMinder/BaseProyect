package com.autominder.autominder.data.database.repository

import com.autominder.autominder.data.database.dao.CarDao
import com.autominder.autominder.data.database.models.CarModel

class CarRepository(private val carDao: CarDao) {
    suspend fun addCar(newCar: CarModel) = carDao.insertCar(newCar)
    suspend fun getMyCars(user_id: Long) = carDao.getMyCars(user_id)
}