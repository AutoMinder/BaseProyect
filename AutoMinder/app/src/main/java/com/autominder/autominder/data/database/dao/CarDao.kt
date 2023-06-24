package com.autominder.autominder.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.autominder.autominder.data.database.models.CarEntity

@Dao
interface CarDao {
//    @Transaction
//    @Query("SELECT * FROM car_table WHERE owner_id = :userId")
//    suspend fun getMyCars(userId: Long): List<CarEntity>

    /*
    * Upsert is a combination of insert and update
     */
    @Upsert
    suspend fun upsertCars(cars: List<CarEntity>)

    @Query("SELECT * FROM car_table")
    fun pagingSource(): PagingSource<Int, CarEntity>

    @Transaction
    @Insert
    suspend fun insertCar(newCar: CarEntity)

    @Query("DELETE FROM car_table")
    suspend fun deleteAllCars()
}