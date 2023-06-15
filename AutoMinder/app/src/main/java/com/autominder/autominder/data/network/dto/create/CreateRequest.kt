package com.autominder.autominder.data.network.dto.create

import java.util.Date

data class CreateRequest(
    val vin: String = "",
    val car_name: String = "",
    val brand: String = "",
    val model: String = "",
    val year: String = "",
    val kilometers: String = "",
    val kilometersDate: String,
    val lastMaintenance: String,
    val mayorTuning: String?,
    val minorTuning: String?,
    val lastOilChange: String,
    val lastCoolantChange: String,

)
