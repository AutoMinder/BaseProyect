package com.autominder.autominder.network.dto.create

import java.util.Date

data class CreateRequest(
    val vin: String = "",
    val car_name: String = "",
    val brand: String = "",
    val model: String = "",
    val year: String = "",
    val kilometers: String = "",
    val kilometersDate: Date,
    val lastMaintenance: Date,
    val mayorTuning: Date,
    val minorTuning: Date,
    val lastOilChange: Date,
    val lastCoolantChange: Date,
    val errorRecord: List<String>,
)
