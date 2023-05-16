package com.autominder.autominder.carinfo.data

class CarMaintenanceRepository(private val dummyManCar: List<CarMaintenanceData>) {
    fun getCarMaintenanceById(id: Int) = dummyManCar.find { it.id == id }
}