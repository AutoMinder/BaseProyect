package com.autominder.autominder.data.network.dto.update

import java.util.Date

data class UpdateRequest(
    val car_name: String = "",
    val kilometers: String = "",
    val lastMaintenance: String = "",
    val mayorTuning: String  = "",
    val minorTuning: String  = "",
    val lastOilChange: String  = "",
    val lastCoolantChange: String  = "",
    val brand: String = "",
    val model: String = "",
    val year: String = "",
)