package com.bbo.mobiledevexam.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(
    tableName = "order_details_table",
    primaryKeys = ["order_id", "product_id"]
    )
data class OrderDetailsTable(

    @ColumnInfo(name = "order_id")
    var orderId: Long,

    @ColumnInfo(name = "product_id")
    var productId: String
)