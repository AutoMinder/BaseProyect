package com.autominder.autominder.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.autominder.autominder.database.models.OwnerAndCarModel

@Dao
interface OwnerAndCarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(_ownerAndCar : OwnerAndCarModel)

}