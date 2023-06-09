package com.autominder.autominder.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "car_table")
data class CarModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "car_id") val carId: Long,
    @ColumnInfo(name = "vin") val vin : String,
    @ColumnInfo(name = "car_name") val car_name : String,
    @ColumnInfo(name = "brand") val brand : String,
    @ColumnInfo(name = "model") val model : String,
    @ColumnInfo(name = "year") val year : String,
    @ColumnInfo(name = "kilometers") val kilometers : String,
    @ColumnInfo(name = "kilometersDate") val kilometers_date : Date,
    @ColumnInfo(name = "lastMaintenance") val last_maintenance : Date,
    @ColumnInfo(name = "mayorTuning") val mayor_tuning : Date,
    @ColumnInfo(name = "minorTuning") val minor_tuning : Date,
    @ColumnInfo(name = "lastOilChange") val last_oil_change : Date,
    @ColumnInfo(name = "lastCoolantChange") val last_coolant_change : Date,
//    TODO: Revisar implementacion de arreglo de errores /* @ColumnInfo(name = "errorRecord") val error_record : List<String>,  */
    @ColumnInfo(name = "hidden") val hidden : Boolean,
    @ColumnInfo(name = "owner_id") val owner : Long
)