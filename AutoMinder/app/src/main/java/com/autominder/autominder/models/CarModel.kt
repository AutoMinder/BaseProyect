package com.autominder.autominder.models

import java.time.LocalDate
import java.util.Date

data class CarModel(
    val id: String,
    val vin: String?,
    val name: String,
    val brand: String,
    val model: String,
    val year: Int?,
    val kilometers: Int?,
    val kilometersDate: LocalDate?,
    val lastMaintenance: String,
    val nextMaintenances: LocalDate?,
    val lastOilChange: String,
    val lastCoolantDate: LocalDate?,
    val lastMayorTuning: LocalDate?,
    val lastMinorTuning: LocalDate?,
    val hidden: Boolean?,
    val errorModel: MutableList<String>?,
)
