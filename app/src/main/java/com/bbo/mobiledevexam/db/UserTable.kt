package com.bbo.mobiledevexam.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserTable(

    @ColumnInfo(name = "user_id")
    val userId: String,

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "user_email")
    val userEmail: String,

    @ColumnInfo(name = "user_name")
    val userName: String? = null

)