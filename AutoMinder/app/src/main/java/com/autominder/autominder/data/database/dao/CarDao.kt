package com.autominder.autominder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.autominder.autominder.data.database.models.CarModel

@Dao
interface CarDao {
    @Transaction
    @Query("SELECT * FROM car_table WHERE owner_id = :userId")
    suspend fun getMyCars(userId: Long): List<CarModel>

    @Transaction
    @Insert
    suspend fun insertCar(newCar: CarModel)
}