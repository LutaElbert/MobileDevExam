package com.bbo.mobiledevexam.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_table")
data class OrderTable(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    val orderId: Int,

    @ColumnInfo(name = "order_date")
    val orderDate: String? = null
)