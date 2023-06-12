package com.autominder.autominder.network.dto.visibility

import com.google.gson.annotations.SerializedName

data class VisibilityResponse(
    @SerializedName("message") val message: String
)
