package com.bbo.mobiledevexam.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "order_details_table")
data class OrderDetailsTable(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "order_id")
    val orderId: String? = null,

    @ColumnInfo(name = "product_id")
    val productId: String? = null,

    @ColumnInfo(name = "order_date")
    val orderDate: String? = null
)