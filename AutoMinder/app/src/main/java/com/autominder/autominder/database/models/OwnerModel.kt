package com.autominder.autominder.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class OwnerModel(
    @PrimaryKey(autoGenerate = true) val userId: Long,
    @ColumnInfo(name = "email") val email : String,
    @ColumnInfo(name = "username") val username : String
) {
    constructor(email:String, username: String) :
            this(
                0,
                email,
                username
            )
}
