package com.autominder.autominder.ui.myCars.data

import com.autominder.autominder.data.models_dummy.CarModel
import java.text.SimpleDateFormat

val formatter = SimpleDateFormat("yyyy-MM-dd")

val myCarsdummy = mutableListOf<CarModel>(
    CarModel(
        name = "Carro de melvin",
        brand = "Nissan",
        year = 2017,
        id = "as01",
        kilometers = 1000,
        lastOilChange = "2021-01-01",
        nextMaintenances = "202-01-01",
        kilometersDate = "2021-01-01",
        errorModel = null,
        hidden = false,
        lastCoolantDate = null,
        lastMayorTuning = null,
        lastMinorTuning = null,
        model = "Sentra",
        vin = "asdasdasd",
        lastMaintenance = "2021-01-01"
    )
)
/*
val emptyCar = CarModel(
    name = "",
    brand = "",
    year = 0,
    id = "",
    kilometers = 0,
    lastOilChange = Date(),
    nextMaintenances = Date(),
    kilometersDate = Date(),
    errorModel = null,
    hidden = false,
    lastCoolantDate = null,
    lastMayorTuning = null,
    lastMinorTuning = null,
    model = ""
)*/