package com.autominder.autominder.data.network.dto.ownCars

import com.autominder.autominder.data.models_dummy.CarModel
import com.google.gson.annotations.SerializedName

data class OwnResponse(
    @SerializedName("posts") val cars: List<CarModel>
)
