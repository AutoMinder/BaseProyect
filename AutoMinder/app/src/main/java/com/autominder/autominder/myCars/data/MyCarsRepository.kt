package com.autominder.autominder.myCars.data

import com.autominder.autominder.models.CarModel

class MyCarsRepository(private val myCarsDummy: List<CarModel>) {
    fun getMyCars() = myCarsDummy

    fun getCarById(id: String) = myCarsDummy.find { it.id == id }
}