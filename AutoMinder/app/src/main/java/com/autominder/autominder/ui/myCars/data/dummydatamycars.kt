package com.autominder.autominder.ui.myCars.data

import com.autominder.autominder.data.models_dummy.CarModel
import java.text.SimpleDateFormat

val formatter = SimpleDateFormat("yyyy-MM-dd")

/*@SerializedName("_id") val id: String,
    @SerializedName("vin") val vin: String?,
    @SerializedName("car_name") val name: String,
    @SerializedName("brand") val brand: String,
    @SerializedName("model") val model: String,
    @SerializedName("year") val year: Int?,
    @SerializedName("kilometers") val kilometers: Int?,
    @SerializedName("kilometersDate") val kilometersDate: String?,
    @SerializedName("lastMaintenance") val lastMaintenance: String,
    val nextMaintenances: String?,
    @SerializedName("lastOilChange") val lastOilChange: String,
    @SerializedName("lastCoolantChange") val lastCoolantDate: String?,
    @SerializedName("mayorTuning") val lastMayorTuning: String?,
    @SerializedName("minorTuning") val lastMinorTuning: String?,
    val hidden: Boolean?,
    @SerializedName("errorRecord") val errorModel: MutableList<String>?,*/
val myCarsdummy = mutableListOf(
    com.autominder.autominder.data.database.models.CarModel(
        vin = "1G1JC5444R7252367",
        car_name = "Chevrolet Cavalier",
        model = "Cavalier",
        brand = "Chevrolet",
        year = "1994",
        kilometers = "100000",
        carId = 1,
        hidden = false,
        kilometers_date = "2021-01-01",
        last_coolant_change = "2021-01-01",
        last_maintenance = "2021-01-01",
        last_oil_change = "2021-01-01",
        mayor_tuning = "2021-01-01",
        minor_tuning = "2021-01-01",
        owner = 1,
    ),
)