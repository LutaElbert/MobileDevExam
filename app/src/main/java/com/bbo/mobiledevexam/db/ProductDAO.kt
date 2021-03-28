package com.bbo.mobiledevexam.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userTable: UserTable) : Maybe<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(orderTable: OrderTable) : Maybe<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCart(order: OrderDetailsTable) : Maybe<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductTable)

    @Update
    suspend fun update(product: ProductTable)

    @Delete
    suspend fun delete(product: ProductTable)

    @Query("DELETE FROM product_item_table")
    suspend fun deleteAll()

    @Query("SELECT count() FROM order_table AS count")
    fun getOrderTableRowCount(): Single<Long>

    @Query("SELECT MAX(order_id) FROM order_table AS maxid")
    fun getMaxOrderTableId(): Single<Long>

    @Query("SELECT MAX(order_id) FROM order_details_table AS maxid")
    fun getMaxOrderID(): Long

    @Query("SELECT * FROM product_item_table")
    fun getAllProducts(): LiveData<List<ProductTable>>

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): Single<List<UserTable>>

    @Query("SELECT * FROM order_table")
    fun getAllOrders() : Single<List<OrderTable>>

    @Query("SELECT * FROM user_table WHERE user_id = :id")
    fun getUserById(id: Long) : Maybe<UserTable>

    @Query("SELECT * FROM order_details_table")
    fun getAllCart() : Single<List<OrderDetailsTable>>

    @Query("SELECT * FROM order_details_table")
    fun getAllCart2() : LiveData<List<OrderDetailsTable>>

    @Query("SELECT * FROM order_details_table WHERE order_id = :orderId")
    fun getCurrentCart(orderId: Long) : LiveData<List<OrderDetailsTable>>

}