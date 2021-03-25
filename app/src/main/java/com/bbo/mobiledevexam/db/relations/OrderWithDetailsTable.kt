package com.bbo.mobiledevexam.db.relations

import androidx.room.*
import com.bbo.mobiledevexam.db.OrderDetailsTable
import com.bbo.mobiledevexam.db.OrderTable

data class OrderWithDetailsTable(

    @Embedded
    val order: OrderTable,

    @Relation(
        parentColumn = "order_id",
        entityColumn = "order_id"
    )
    val orderDetails: List<OrderDetailsTable>
)