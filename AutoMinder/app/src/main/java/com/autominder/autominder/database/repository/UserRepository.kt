package com.autominder.autominder.database.repository

import com.autominder.autominder.database.dao.UserDao
import com.autominder.autominder.database.models.UserModel

class UserRepository(private val userDao:UserDao) {
    suspend fun addUser(newUser: UserModel) = userDao.insertUser(newUser)
}