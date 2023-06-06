package com.autominder.autominder.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.autominder.autominder.database.models.CarModel
import com.autominder.autominder.database.models.OwnerAndCarModel

@Dao
interface CarDao {

    @Query("SELECT * FROM car_table")
    suspend fun getAllCars(): List<CarModel>

    @Query("SELECT * FROM car_table WHERE owner = :_ownerId")
    suspend fun getMyCars(_ownerId: Long): OwnerAndCarModel?

    @Transaction
    @Insert
    suspend fun insertCar(_newCar: CarModel)
}