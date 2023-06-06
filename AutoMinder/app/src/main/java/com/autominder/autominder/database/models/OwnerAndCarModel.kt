package com.autominder.autominder.database.models

import androidx.room.Embedded
import androidx.room.Relation

data class OwnerAndCarModel(
    @Embedded val user: OwnerModel,
    @Relation(
        parentColumn = "userId",
        entityColumn = "owner"
    ) val cars: List<CarModel>
)
