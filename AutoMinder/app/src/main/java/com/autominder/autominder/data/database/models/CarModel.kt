package com.autominder.autominder.data.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "car_table")
data class CarModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "car_id") val carId: Long,
    @ColumnInfo(name = "vin") val vin : String,
    @ColumnInfo(name = "car_name") val car_name : String,
    @ColumnInfo(name = "brand") val brand : String,
    @ColumnInfo(name = "model") val model : String,
    @ColumnInfo(name = "year") val year : String,
    @ColumnInfo(name = "kilometers") val kilometers : String,
    @ColumnInfo(name = "kilometersDate") val kilometers_date : String,
    @ColumnInfo(name = "lastMaintenance") val last_maintenance : String,
    @ColumnInfo(name = "mayorTuning") val mayor_tuning : String,
    @ColumnInfo(name = "minorTuning") val minor_tuning : String,
    @ColumnInfo(name = "lastOilChange") val last_oil_change : String,
    @ColumnInfo(name = "lastCoolantChange") val last_coolant_change : String,
//    @ColumnInfo(name = "errorRecord") val error_record : MutableList<String>,
    @ColumnInfo(name = "hidden") val hidden : Boolean,
    @ColumnInfo(name = "owner_id") val owner : Long
) : Parcelable