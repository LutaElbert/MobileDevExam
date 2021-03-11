package com.bbo.mobiledevexam.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_item_table")
data class OrderTable(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    val orderId: Int,

    @ColumnInfo(name = "product_name")
    val productName: String? = null,

    @ColumnInfo(name = "product_category")
    val productCategory: String? = null,

    @ColumnInfo(name = "product_price")
    val productPrice: Double? = null,

    @ColumnInfo(name = "product_color")
    var productColor: String? = null
)