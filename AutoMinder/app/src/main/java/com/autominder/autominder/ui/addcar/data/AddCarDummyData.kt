package com.autominder.autominder.ui.addcar.data

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

val brands = mutableListOf("Toyota", "Nissan", "Hyundai", "Isuzu", "BMW", "Mazda")
val models = mutableListOf("Yaris", "Corolla", "Prius", "Camry", "Tacoma")