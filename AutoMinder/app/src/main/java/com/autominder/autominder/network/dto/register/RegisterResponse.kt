package com.autominder.autominder.network.dto.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(
    @SerializedName("message") val message: String
)