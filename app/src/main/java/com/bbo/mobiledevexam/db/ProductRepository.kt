package com.bbo.mobiledevexam.db

class ProductRepository(private val dao: ProductDAO) {

    val products = dao.getAllProducts()

    val users = dao.getUsers()

    suspend fun insertCart(cart: CartTable) {
        dao.insertCart(cart)
    }

    suspend fun deleteCart(cart: CartTable) {
        dao.deleteCart(cart)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    fun insertUser(userTable: UserTable)  = dao.insertUser(userTable)

}