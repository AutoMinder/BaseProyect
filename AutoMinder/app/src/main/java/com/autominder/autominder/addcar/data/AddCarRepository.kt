package com.autominder.autominder.addcar.data

class AddCarRepository(
    //TODO()
    private val cars: MutableList<CarModel>,
    private val brands: MutableList<String>,
    private val models: MutableList<String>
) {

    fun getCars() = cars
    fun addCar(car: CarModel) = cars.add(car)

    fun getCarBrands() = brands

    fun getCarModels() = models
}