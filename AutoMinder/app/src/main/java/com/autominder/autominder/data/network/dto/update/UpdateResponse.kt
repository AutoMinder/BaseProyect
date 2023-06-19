package com.autominder.autominder.data.network.dto.update

import com.google.gson.annotations.SerializedName

data class UpdateResponse(
    @SerializedName("message") val message: String
)