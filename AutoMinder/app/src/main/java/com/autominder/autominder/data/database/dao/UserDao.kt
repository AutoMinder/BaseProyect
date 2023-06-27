package com.autominder.autominder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.autominder.autominder.data.database.models.UserModel

@Dao
interface UserDao {
    @Transaction
    @Insert
    suspend fun insertUser(newUser: UserModel)
}