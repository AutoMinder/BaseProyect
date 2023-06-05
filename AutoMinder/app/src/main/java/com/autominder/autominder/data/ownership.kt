package com.autominder.autominder.data

import androidx.room.Embedded
import androidx.room.Relation

data class ownership(
    @Embedded val user: userModel,
    @Relation(
        parentColumn = "userId",
        entityColumn = "owner"
    ) val cars: List<carModel>
)
