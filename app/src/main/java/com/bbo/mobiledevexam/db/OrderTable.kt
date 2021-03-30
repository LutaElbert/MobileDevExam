package com.bbo.mobiledevexam.db

import androidx.room.*
import java.time.LocalDate

@Entity(tableName = "order_table")
data class OrderTable(

    @PrimaryKey
    @ColumnInfo(name = "order_id")
    var orderId: Long? = null,

    @ColumnInfo(name = "user_email")
    val userEmail: String? = null,

    @Embedded
    val products: CartTable? = null,

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
