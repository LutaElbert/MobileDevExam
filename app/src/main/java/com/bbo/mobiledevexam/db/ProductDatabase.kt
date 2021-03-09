package com.bbo.mobiledevexam.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CartTable::class], version = 1)
abstract class ProductDatabase: RoomDatabase() {

    abstract val productDAO: ProductDAO

    companion object {

        private const val DATABASE_NAME = "product_database"

        @Volatile
        private var INSTANCE: ProductDatabase? = null

        fun getInstance(context: Context): ProductDatabase {
            synchronized(this) {
                var instance = INSTANCE
                instance?.let {
                    return it
                }

                instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProductDatabase::class.java,
                    DATABASE_NAME
                ).build()

                return instance
            }
        }
    }
}