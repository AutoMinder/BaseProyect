package com.autominder.autominder.data.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "car_table")
data class CarModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "car_id") val carId: Long,
    @SerializedName("_id")@ColumnInfo(name = "_id") val idMongo: String,
    @SerializedName("vin") @ColumnInfo(name = "vin") val vin : String?,
    @SerializedName("car_name") @ColumnInfo(name = "car_name") val car_name : String,
    @SerializedName("brand") @ColumnInfo(name = "brand") val brand : String,
    @SerializedName("model") @ColumnInfo(name = "model") val model : String,
    @SerializedName("year") @ColumnInfo(name = "year") val year : String,
    @SerializedName("kilometers") @ColumnInfo(name = "kilometers") val kilometers : String,

//  This field does NOT exist in online database:
//  @ColumnInfo(name = "kilometersDate") val kilometers_date : String?,

    @SerializedName("lastMaintenance") @ColumnInfo(name = "lastMaintenance") val last_maintenance : String?,
    @SerializedName("mayorTuning") @ColumnInfo(name = "mayorTuning") val mayor_tuning : String?,
    @SerializedName("minorTuning") @ColumnInfo(name = "minorTuning") val minor_tuning : String?,
    @SerializedName("lastOilChange") @ColumnInfo(name = "lastOilChange") val last_oil_change : String?,
    @SerializedName("lastCoolantChange") @ColumnInfo(name = "lastCoolantChange") val last_coolant_change : String?,
//    @ColumnInfo(name = "errorRecord") val error_record : MutableList<String>,
    @SerializedName("hidden")@ColumnInfo(name = "hidden") val hidden : Boolean,
    @ColumnInfo(name = "owner_id") val owner : Long
) : Parcelable