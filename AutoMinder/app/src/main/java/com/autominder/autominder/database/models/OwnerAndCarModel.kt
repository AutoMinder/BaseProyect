package com.autominder.autominder.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "owner_table")
data class OwnerAndCarModel(
    @Embedded val user: OwnerModel,
    @Relation(
        parentColumn = "userId",
        entityColumn = "owner"
    ) val cars: List<CarModel>
)
