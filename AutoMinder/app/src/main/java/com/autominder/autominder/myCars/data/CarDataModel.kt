package com.autominder.autominder.myCars.data

data class CarDataModel(
    val id: Int,
    val name: String,
    val brand: String,
    val model: String,
    val year: String,
    val kilometers: String,
    val kilometersDate: String,
    val owner: String,
    val hidden: Boolean
)
