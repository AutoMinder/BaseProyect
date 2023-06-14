package com.autominder.autominder.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.autominder.autominder.data.database.models.CarModel
import com.autominder.autominder.data.database.models.UserWithCars

@Dao
interface CarDao {
    @Transaction
    @Query("SELECT * FROM user_table WHERE user_id = :userId")
    suspend fun getMyCars(userId: Long): List<UserWithCars>

    @Transaction
    @Insert
    suspend fun insertCar(newCar: CarModel)

    @Transaction
    @Query("SELECT * FROM car_table WHERE car_id = :carId")
    fun getCarById(carId: String): CarModel
}