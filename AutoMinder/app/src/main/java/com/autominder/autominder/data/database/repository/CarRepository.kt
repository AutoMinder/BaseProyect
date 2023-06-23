package com.autominder.autominder.data.database.repository

import com.autominder.autominder.data.database.dao.CarDao
import com.autominder.autominder.data.database.models.CarEntity

class CarRepository(private val carDao: CarDao) {
    suspend fun addCar(newCar: CarEntity) = carDao.insertCar(newCar)
    suspend fun getMyCars() = carDao.pagingSource()
}