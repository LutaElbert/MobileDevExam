package com.bbo.mobiledevexam.db

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductRepository(private val dao: ProductDAO) {

    val products = dao.getAllProducts()

    val users = dao.getUsers()

    val orders = dao.getOrders()

    suspend fun insertCart(cart: CartTable) {
        dao.insertCart(cart)
    }

    suspend fun deleteCart(cart: CartTable) {
        dao.deleteCart(cart)
    }

    fun deleteAllFromCart() {
        Single.fromCallable {
            dao.deleteAll()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun insertUser(userTable: UserTable)  = dao.insertUser(userTable)

    fun insertOrder(orderTable: OrderTable)  = dao.insertOrder(orderTable)

    fun insertAllOrder(orders: List<OrderTable>)  = dao.insertAllOrder(orders)

    fun getOrderTableRowCount() = dao.getOrderTableRowCount()

    fun getMaxOrderTableId() = dao.getMaxOrderTableId()

    fun findOrders(orderId: Long) = dao.findOrders(orderId)

}