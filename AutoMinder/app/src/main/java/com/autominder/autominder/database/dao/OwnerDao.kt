package com.autominder.autominder.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.autominder.autominder.database.models.OwnerModel

@Dao
interface OwnerDao {

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<OwnerModel>

    @Transaction
    @Insert
    suspend fun insertUser(newUser: OwnerModel)
}