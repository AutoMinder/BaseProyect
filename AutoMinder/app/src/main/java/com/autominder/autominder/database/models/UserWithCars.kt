package com.autominder.autominder.database.models

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

data class UserWithCars(
    @Embedded val user: UserModel,
    @Relation(
        parentColumn = "user_id",
        entityColumn = "owner_id"
    ) val cars: List<CarModel>
)
