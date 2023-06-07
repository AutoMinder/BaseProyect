package com.autominder.autominder.addcar.data

import com.autominder.autominder.models.CarModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

private val defaultDate = LocalDate.parse("2021-01-01", DateTimeFormatter.ISO_DATE)

val car1 =
    CarModel("1","1","Carro de Juan", "Toyota", "Corolla",2019, 70000, defaultDate, defaultDate, defaultDate, defaultDate, defaultDate, defaultDate, defaultDate, false, mutableListOf("sdasdasd", "asdasdasd"))

val carsDummy = mutableListOf(car1)

val brands = mutableListOf("Toyota", "Nissan", "Hyundai", "Isuzu", "BMW", "Mazda")
val models = mutableListOf("Yaris", "Corolla", "Prius", "Camry", "Tacoma")