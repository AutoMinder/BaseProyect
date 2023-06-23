package com.autominder.autominder.ui.myCars.data

class MyCarsRepository(private val myCars: List<com.autominder.autominder.data.database.models.CarEntity>) {

    fun getMyCars() = myCarsdummy

    //fun getCarById(id: String) = myCars.find { it.id == id }
}