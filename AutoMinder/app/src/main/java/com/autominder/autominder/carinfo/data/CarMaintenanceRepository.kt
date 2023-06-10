package com.autominder.autominder.carinfo.data

import com.autominder.autominder.models.CarModel

class CarMaintenanceRepository(private val dummyManCar: MutableList<CarModel>) {
    fun getCarMaintenanceById(id: String) = dummyManCar.find { it.id == id }

    fun getCarMaintenanceByCarId(carId: String) = dummyManCar.filter { it.id == carId }
}