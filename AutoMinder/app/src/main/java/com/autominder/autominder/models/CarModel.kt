package com.autominder.autominder.models

import java.util.Date

data class CarModel(
    val id: String,
    val name: String,
    val brand: String,
    val model: String,
    val year: Int,
    val kilometers: Int,
    val kilometersDate: Date?,
    val lastMaintenance: Date,
    val nextMaintenances: Date?,
    val lastOilChange: Date,
    val lastCoolantDate: Date?,
    val lastMayorTuning: Date?,
    val lastMinorTuning: Date?,
    val hidden: Boolean?,
    val errorModel: MutableList<String>?,
)
