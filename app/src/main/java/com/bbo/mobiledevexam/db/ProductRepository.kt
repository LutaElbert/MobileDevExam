package com.bbo.mobiledevexam.db

class ProductRepository(private val dao: ProductDAO) {

    val products = dao.getAllProducts()

    suspend fun insert(product: ProductTable) {
        dao.insert(product)
    }

    suspend fun update(product: ProductTable) {
        dao.update(product)
    }

    suspend fun delete(product: ProductTable) {
        dao.delete(product)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }
}