package com.autominder.autominder.database.repository

import com.autominder.autominder.database.dao.OwnerDao
import com.autominder.autominder.database.models.OwnerModel

class OwnerRepository(private val userDao:OwnerDao) {
    suspend fun getUsers() = userDao.getAllUsers()

    suspend fun addUser(newUser: OwnerModel) = userDao.insertUser(newUser)
}