package com.autominder.autominder.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "car_table")
data class carModel(
    @PrimaryKey(autoGenerate = true) val carId: Long,
    @ColumnInfo(name = "vin") val vin : String,
    @ColumnInfo(name = "car_name") val car_name : String,
    @ColumnInfo(name = "brand") val brand : String,
    @ColumnInfo(name = "year") val year : String,
    @ColumnInfo(name = "kilometers") val kilometers : String,
    @ColumnInfo(name = "kilometers_date") val kilometers_date : Date,
    @ColumnInfo(name = "hidden") val hidden : Boolean,
    @ColumnInfo(name = "owner") val owner : Long
) {
    constructor(vin: String, car_name: String, brand: String, year: String, kilometers: String, kilometers_date: Date, owner: Long) :
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

