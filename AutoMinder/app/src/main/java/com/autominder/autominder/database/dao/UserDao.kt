package com.autominder.autominder.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.autominder.autominder.database.models.UserModel

@Dao
interface UserDao {
    @Transaction
    @Insert
    suspend fun insertUser(newUser: UserModel)
}