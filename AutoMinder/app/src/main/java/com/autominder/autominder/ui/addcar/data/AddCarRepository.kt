package com.autominder.autominder.ui.addcar.data

class AddCarRepository(
    //TODO()
    private val cars: MutableList<com.autominder.autominder.data.database.models.CarEntity>,
    private val brands: MutableList<String>,
    private val models: MutableList<String>
) {

    fun getCars() = cars
    //fun addCar(car: CarModel) = cars.add(car)

    fun getCarBrands() = brands

    fun getCarModels() = models
}