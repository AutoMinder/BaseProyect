package com.autominder.autominder.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserModel(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "user_id") val userId: Long,
    @ColumnInfo(name = "email") val email : String,
    @ColumnInfo(name = "username") val username : String,
    @ColumnInfo(name = "roles") val roles : String,
    @ColumnInfo(name = "token") val token : String
)