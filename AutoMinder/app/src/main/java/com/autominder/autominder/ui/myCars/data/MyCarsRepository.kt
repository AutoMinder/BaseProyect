package com.autominder.autominder.ui.myCars.data

import com.autominder.autominder.data.models_dummy.CarModel

class MyCarsRepository(private val myCars: List<CarModel>) {

    fun getMyCars() = myCars

    fun getCarById(id: String) = myCars.find { it.id == id }
}