package com.bbo.mobiledevexam.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Single

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(cart: CartTable)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertUser(userTable: UserTable): Single<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrder(orderTable: OrderTable): Single<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllOrder(vararg orders: OrderTable): Single<List<Long>>

    @Query("SELECT count() FROM order_table AS count")
    fun getOrderTableRowCount(): Single<Long>

    @Query("SELECT MAX(order_id) FROM order_table AS maxid")
    fun getMaxOrderTableId(): Single<Long>

    @Delete
    suspend fun deleteCart(cart: CartTable)

    @Query("DELETE FROM product_item_table")
    fun deleteAll()

    @Query("SELECT * FROM user_table")
    fun getUsers(): Single<List<UserTable>>

    @Query("SELECT * FROM product_item_table")
    fun getAllProducts(): LiveData<List<CartTable>>

    @Query("SELECT * FROM order_table")
    fun getOrders(): Single<List<OrderTable>>
}