package com.autominder.autominder.data.network.dto.ownCars

data class OwnCarDTO(
    val carId: Long,
    val vin : String,
    val car_name : String,
    val brand : String,
    val model : String,
    val year : String,
    val kilometers : String,
    val kilometers_date : String,
    val last_maintenance : String,
    val mayor_tuning : String,
    val minor_tuning : String,
    val last_oil_change : String,
    val last_coolant_change : String
)
