package com.autominder.autominder.data.network.dto.ownCars

import com.autominder.autominder.data.database.models.CarModel
import com.google.gson.annotations.SerializedName

data class OwnCarsListResponse(
    @SerializedName("posts") val cars: List<CarModel>,
    @SerializedName("currentPage") val currentPage: Int,
    @SerializedName("totalPages") val totalPages: Int
)