package com.autominder.autominder.myCars.data

class MyCarsRepository(private val myCarsDummy: List<CarDataModel>) {
    fun getMyCars () = myCarsDummy

    fun getCarById(id: Int) = myCarsDummy.find { it.id == id }
}