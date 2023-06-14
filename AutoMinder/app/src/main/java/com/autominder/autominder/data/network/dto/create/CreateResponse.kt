package com.autominder.autominder.data.network.dto.create

import com.google.gson.annotations.SerializedName
import java.sql.Date

data class CreateResponse(
    @SerializedName("vin") val vin: String,
    @SerializedName("car_name") val car_name: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("model") val model: String,
    @SerializedName("year") val year: String,
    @SerializedName("kilometers") val kilometers: String,
    @SerializedName("kilometersDate") val kilometersDate: Date,
    @SerializedName("lastMaintenance") val lastMaintenance: Date,
    @SerializedName("mayorTuning") val mayorTuning: Date,
    @SerializedName("minorTuning") val minorTuning: Date,
    @SerializedName("lastOilChange") val lastOilChange: Date,
    @SerializedName("lastCoolantChange") val lastCoolantChange: Date,
    @SerializedName("errorRecord") val errorRecord: List<String>,
    @SerializedName("hidden") val hidden: Boolean,
    @SerializedName("_id") val id: String,
    @SerializedName("user") val user: String,

    )
