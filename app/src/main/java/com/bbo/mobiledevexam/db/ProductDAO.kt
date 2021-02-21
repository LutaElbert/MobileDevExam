package com.bbo.mobiledevexam.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProductDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: ProductTable)

    @Update
    suspend fun update(product: ProductTable)

    @Delete
    suspend fun delete(product: ProductTable)

    @Query("DELETE FROM product_item_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM product_item_table")
    fun getAllProducts(): LiveData<List<ProductTable>>
}