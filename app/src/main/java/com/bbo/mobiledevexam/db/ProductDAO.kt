package com.bbo.mobiledevexam.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bbo.mobiledevexam.db.relations.OrderWithDetailsTable
import io.reactivex.Maybe
import io.reactivex.Single

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(userTable: UserTable) : Maybe<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: ProductTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(orderTable: OrderTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(order: OrderDetailsTable)

    @Update
    suspend fun update(product: ProductTable)

    @Delete
    suspend fun delete(product: ProductTable)

    @Query("DELETE FROM product_item_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM product_item_table")
    fun getAllProducts(): LiveData<List<ProductTable>>

    @Query("SELECT * FROM user_table")
    fun getAllUsers(): Single<List<UserTable>>

    @Query("SELECT * FROM user_table WHERE user_id = :id")
    fun getUserById(id: Long) : Maybe<UserTable>

    @Transaction
    @Query("SELECT * FROM order_table")
    fun getAllOrders(): LiveData<List<OrderWithDetailsTable>>
}