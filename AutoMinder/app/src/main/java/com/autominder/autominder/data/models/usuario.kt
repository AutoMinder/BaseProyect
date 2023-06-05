package com.autominder.autominder.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_table")
data class userModel(
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
