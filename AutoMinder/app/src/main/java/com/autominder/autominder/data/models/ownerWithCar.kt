package com.autominder.autominder.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class ownerWithCar(
    @Embedded val user: userModel,
    @Relation(
        parentColumn = "userId",
        entityColumn = "owner"
    ) val cars: List<carModel>
)
