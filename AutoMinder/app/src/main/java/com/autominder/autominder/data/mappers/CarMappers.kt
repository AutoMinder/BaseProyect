package com.autominder.autominder.data.mappers

import com.autominder.autominder.data.database.models.CarEntity
import com.autominder.autominder.data.domain.CarModel
import com.autominder.autominder.data.network.dto.ownCars.OwnCarDTO

fun OwnCarDTO.toCarEntity(): CarEntity{
    return CarEntity(
        carId = this.carId,
        vin = this.vin,
        car_name = this.car_name,
        brand = this.brand,
        model = this.model,
        year = this.year,
        kilometers = this.kilometers,
        kilometers_date = this.kilometers_date,
        last_maintenance = this.last_maintenance,
        mayor_tuning = this.mayor_tuning,
        minor_tuning = this.minor_tuning,
        last_oil_change = this.last_oil_change,
        last_coolant_change = this.last_coolant_change
    )
}

fun CarEntity.toCarModel(): CarModel {
    return CarModel(
        carId = this.carId,
        vin = this.vin,
        car_name = this.car_name,
        brand = this.brand,
        model = this.model,
        year = this.year,
        kilometers = this.kilometers,
        kilometers_date = this.kilometers_date,
        last_maintenance = this.last_maintenance,
        mayor_tuning = this.mayor_tuning,
        minor_tuning = this.minor_tuning,
        last_oil_change = this.last_oil_change,
        last_coolant_change = this.last_coolant_change
    )
}