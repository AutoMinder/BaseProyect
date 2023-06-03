package com.autominder.autominder.myCars.data

import com.autominder.autominder.models.CarModel
import java.text.SimpleDateFormat
import java.util.Date

val formatter = SimpleDateFormat("yyyy-MM-dd")

val myCarsdummy = mutableListOf<CarModel>(
    CarModel(
        name = "Carro de melvin",
        brand = "Nissan",
        year = 2017,
        id = "as01",
        kilometers = 1000,
        lastOilChange = formatter.parse("2021-01-01") as Date,
        nextMaintenances = formatter.parse("202-01-01") as Date,
        kilometersDate = formatter.parse("2021-01-01") as Date,
        errorModel = null,
        hidden = false,
        lastCoolantDate = null,
        lastMayorTuning = null,
        lastMinorTuning = null,
        model = "Sentra"
    )
)

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
)