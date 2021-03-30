package com.bbo.mobiledevexam.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "order_table")
data class OrderTable(

    @PrimaryKey
    @ColumnInfo(name = "order_id")
    var orderId: Long? = null,

    @ColumnInfo(name = "order_date")
    val orderDate: String? = LocalDate.now().toString()
) {

    companion object {
        const val START_ID = 100000.toLong()
    }

    @Ignore
    constructor(orderId: Long) : this() {
        this.orderId = orderId
    }
}
