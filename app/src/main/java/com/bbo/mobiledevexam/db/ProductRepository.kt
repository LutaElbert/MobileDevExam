package com.bbo.mobiledevexam.db

class ProductRepository(private val dao: ProductDAO) {

    val products = dao.getAllProducts()

    suspend fun insert(cart: CartTable) {
        dao.insert(cart)
    }

    suspend fun update(cart: CartTable) {
        dao.update(cart)
    }

    suspend fun delete(cart: CartTable) {
        dao.delete(cart)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}