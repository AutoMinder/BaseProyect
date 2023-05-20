package com.autominder.autominder.addcar.data

class AddCarRepository(private val cars: MutableList<CarModel>) {

    fun getCars() = cars
    fun addCar(car: CarModel) = cars.add(car)
}