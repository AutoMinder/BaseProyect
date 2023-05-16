package com.autominder.autominder.carinfo.data

data class CarMaintenanceData(
    val id: Int,
    val name: String,
    val description: String,
    val mileage: Int,
    val date: String,
    val cost: Double,
    val carId: Int
)
