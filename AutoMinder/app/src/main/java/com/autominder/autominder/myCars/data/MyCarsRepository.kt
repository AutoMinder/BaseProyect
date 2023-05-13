package com.autominder.autominder.myCars.data

class MyCarsRepository(private val myCarsDummy: List<CarDataModel>) {
    fun getMyCars () = myCarsDummy
}