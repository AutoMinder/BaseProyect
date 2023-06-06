package com.autominder.autominder.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "car_table")
data class CarModel(
    @PrimaryKey(autoGenerate = true) val carId: Long,
    @ColumnInfo(name = "vin") val vin : String,
    @ColumnInfo(name = "car_name") val car_name : String,
    @ColumnInfo(name = "brand") val brand : String,
    @ColumnInfo(name = "year") val year : String,
    @ColumnInfo(name = "kilometers") val kilometers : String,
    @ColumnInfo(name = "kilometers_date") val kilometers_date : String,
    @ColumnInfo(name = "hidden") val hidden : Boolean,
    @ColumnInfo(name = "owner") val owner : Long
) {
    constructor(vin: String, car_name: String, brand: String, year: String, kilometers: String, kilometers_date: String, owner: Long) :
        this(
            0,
            vin,
            car_name,
            brand,
            year,
            kilometers,
            kilometers_date,
            false,
            owner
        )
}

