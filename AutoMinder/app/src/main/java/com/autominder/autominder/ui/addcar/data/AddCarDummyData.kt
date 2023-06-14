package com.autominder.autominder.ui.addcar.data

import com.autominder.autominder.data.models_dummy.CarModel
import java.util.Calendar
import java.util.Date

fun generateDummyDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.YEAR, 2023)
    calendar.set(Calendar.MONTH, Calendar.JUNE)
    calendar.set(Calendar.DAY_OF_MONTH, 7)
    return calendar.time
}


private val defaultDate = generateDummyDate()

val car1 =
    CarModel("1","1","Carro de Juan", "Toyota", "Corolla",2019, 70000, "defaultDate", "defaultDate", "defaultDate", "defaultDate", "defaultDate", "defaultDate", "defaultDate", false, mutableListOf("sdasdasd", "asdasdasd"))

val carsDummy = mutableListOf(car1)

val brands = mutableListOf("Toyota", "Nissan", "Hyundai", "Isuzu", "BMW", "Mazda")
val models = mutableListOf("Yaris", "Corolla", "Prius", "Camry", "Tacoma")