package com.autominder.autominder.ui.carinfo.data

import com.autominder.autominder.data.models_dummy.CarModel

class CarMaintenanceRepository(private val dummyManCar: MutableList<CarModel>) {
    fun getCarMaintenanceById(id: String) = dummyManCar.find { it.id == id }

    fun getCarMaintenanceByCarId(carId: String) = dummyManCar.filter { it.id == carId }
}