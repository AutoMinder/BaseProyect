package com.autominder.autominder.data.network.dto.whoami

import com.google.gson.annotations.SerializedName

data class WhoamiResponse(
    @SerializedName("_id") val id: String,
    @SerializedName("username") val username: String,
    @SerializedName("email") val email: String
)