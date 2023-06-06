package com.autominder.autominder.database.repository

import com.autominder.autominder.database.dao.OwnerAndCarDao
import com.autominder.autominder.database.models.OwnerAndCarModel

class OwnerAndCarRepository(private val ownerAndCarDao: OwnerAndCarDao) {
    suspend fun addCarToOwner(ownerCar: OwnerAndCarModel) = ownerAndCarDao.insert(ownerCar)
}