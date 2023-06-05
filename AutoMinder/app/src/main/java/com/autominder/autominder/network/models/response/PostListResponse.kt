package com.autominder.autominder.network.models.response

import java.util.Date

class PostListResponse (

    val _id: String? = null,
    val vin: String? = null,
    val car_name: String? = null,
    val brand: String? = null,
    val model: String? = null,
    val year: String? = null,
    val kilometers: String? = null,
    val kilometersDate: Date? = null,
    val lastMaintenance: Date? = null,
    val mayorTuning: Date? = null,
    val minorTuning: Date? = null,
    val lastOilChange: Date? = null,
    val lastCoolantChange: Date? = null,
    val errorRecord: ArrayList<String>? = null,
    val hidden: Boolean? = null,
    val user: Array<String>? = null,
    val createdAt: Date? = null,
    val updatedAt: Date? = null,

)