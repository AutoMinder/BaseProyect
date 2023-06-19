package com.autominder.autominder.data.network.dto.create

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class CreateResponse(
    @SerializedName("vin") val vin: String,
    @SerializedName("car_name") val car_name: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("model") val model: String,
    @SerializedName("year") val year: Int,
    @SerializedName("kilometers") val kilometers: String,
    @SerializedName("lastMaintenance") val lastMaintenance: String,
    @SerializedName("mayorTuning") val mayorTuning: String?,
    @SerializedName("minorTuning") val minorTuning: String?,
    @SerializedName("lastOilChange") val lastOilChange: String,
    @SerializedName("lastCoolantChange") val lastCoolantChange: String,
    @SerializedName("errorRecord") val errorRecord: List<String>,
    @SerializedName("hidden") val hidden: Boolean,
    @SerializedName("_id") val id: String,
    @SerializedName("user") val user: String,

    )
