package com.autominder.autominder.ui.addcar.data

import com.autominder.autominder.data.models_dummy.CarModel

class AddCarRepository(
    //TODO()
    private val cars: MutableList<com.autominder.autominder.data.database.models.CarModel>,
    private val brands: MutableList<String>,
    private val models: MutableList<String>
) {

    fun getCars() = cars
    //fun addCar(car: CarModel) = cars.add(car)

    fun getCarBrands() = brands

    fun getCarModels() = models
}