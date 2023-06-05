package com.autominder.autominder.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.autominder.autominder.addcar.data.CarModel
import com.autominder.autominder.data.models.carModel
import com.autominder.autominder.data.models.ownerWithCar
import com.autominder.autominder.data.models.userModel

@Dao
interface userDao {

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<userModel>

    @Transaction
    @Insert
    suspend fun insertUser(newUser: userModel)
}