package com.bbo.mobiledevexam.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cart: CartTable)

    @Update
    suspend fun update(cart: CartTable)

    @Delete
    suspend fun delete(cart: CartTable)

    @Query("DELETE FROM product_item_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM product_item_table")
    fun getAllProducts(): LiveData<List<CartTable>>
}