package com.autominder.autominder.data.network.dto.ownCars


import com.autominder.autominder.data.database.models.CarEntity
import com.google.gson.annotations.SerializedName

data class OwnCarsListResponse(
    @SerializedName("posts") val cars: List<CarEntity>,
    @SerializedName("currentPage") val page: Int,
    @SerializedName("totalPages") val limit: Int
)
