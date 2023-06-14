package com.autominder.autominder.ui.myCars.data

import com.autominder.autominder.data.models_dummy.CarModel

class MyCarsRepository(private val myCarsDummy: List<CarModel>) {
    fun getMyCars() = myCarsDummy

    fun getCarById(id: String) = myCarsDummy.find { it.id == id }
}