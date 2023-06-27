package com.autominder.autominder.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
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

    @Query("DELETE FROM car_table")
    suspend fun deleteAllCars()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCars(cars: List<CarModel>)

    @Query("SELECT * FROM car_table")
    fun pagingSource(): PagingSource<Int, CarModel>
}