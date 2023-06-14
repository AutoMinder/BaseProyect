package com.autominder.autominder.data.models_dummy

data class CarModel(
    val id: String,
    val vin: String?,
    val name: String,
    val brand: String,
    val model: String,
    val year: Int?,
    val kilometers: Int?,
    val kilometersDate: String?,
    val lastMaintenance: String,
    val nextMaintenances: String?,
    val lastOilChange: String,
    val lastCoolantDate: String?,
    val lastMayorTuning: String?,
    val lastMinorTuning: String?,
    val hidden: Boolean?,
    val errorModel: MutableList<String>?,
)
