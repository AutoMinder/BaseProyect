package com.autominder.autominder.data.database.repository

import com.autominder.autominder.data.database.dao.UserDao
import com.autominder.autominder.data.database.models.UserModel

class UserRepository(private val userDao: UserDao) {
    suspend fun addUser(newUser: UserModel) = userDao.insertUser(newUser)
}