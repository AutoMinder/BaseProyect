package com.autominder.autominder.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.autominder.autominder.addcar.data.CarModel
import com.autominder.autominder.data.models.carModel
import com.autominder.autominder.data.models.ownerWithCar

@Dao
interface ownerWithCarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ownerWithCar : ownerWithCar)

}