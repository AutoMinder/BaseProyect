package com.autominder.autominder.data.models_dummy

import com.google.gson.annotations.SerializedName

data class CarModel(
    @SerializedName("_id") val id: String,
    @SerializedName("vin") val vin: String?,
    @SerializedName("car_name") val name: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("model") val model: String,
    @SerializedName("year") val year: Int?,
    @SerializedName("kilometers") val kilometers: Int?,
    @SerializedName("kilometersDate") val kilometersDate: String?,
    @SerializedName("lastMaintenance") val lastMaintenance: String,
    val nextMaintenances: String?,
    @SerializedName("lastOilChange") val lastOilChange: String,
    @SerializedName("lastCoolantChange") val lastCoolantDate: String?,
    @SerializedName("mayorTuning") val lastMayorTuning: String?,
    @SerializedName("minorTuning") val lastMinorTuning: String?,
    val hidden: Boolean?,
    @SerializedName("errorRecord") val errorModel: MutableList<String>?,
)
