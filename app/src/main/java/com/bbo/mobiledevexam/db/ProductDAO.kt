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

    @Delete
    suspend fun deleteCart(cart: CartTable)

    @Query("DELETE FROM product_item_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM user_table")
    fun getUsers(): Single<List<UserTable>>

    @Query("SELECT * FROM product_item_table")
    fun getAllProducts(): LiveData<List<CartTable>>
}