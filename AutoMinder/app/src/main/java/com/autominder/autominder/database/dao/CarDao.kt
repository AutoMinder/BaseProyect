package com.autominder.autominder.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.autominder.autominder.database.models.CarModel
import com.autominder.autominder.database.models.UserWithCars

@Dao
interface CarDao {
    @Transaction
    @Query("SELECT * FROM user_table WHERE user_id = :userId")
    suspend fun getMyCars(userId: Long): List<UserWithCars>

    @Transaction
    @Insert
    suspend fun insertCar(newCar: CarModel)
}