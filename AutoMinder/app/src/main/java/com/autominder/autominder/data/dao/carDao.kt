package com.autominder.autominder.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.autominder.autominder.addcar.data.CarModel
import com.autominder.autominder.data.models.carModel
import com.autominder.autominder.data.models.ownerWithCar

@Dao
interface carDao {

    @Query("SELECT * FROM car_table")
    suspend fun getAllCars(): List<carModel>

    @Query("SELECT * FROM car_table WHERE owner = :ownerId")
    suspend fun getMyCars(ownerId: Long): ownerWithCar?

    @Transaction
    @Insert
    suspend fun insertCar(newCar: CarModel)
}