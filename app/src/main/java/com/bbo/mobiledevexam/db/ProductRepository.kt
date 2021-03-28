package com.bbo.mobiledevexam.db

import android.util.Log
import androidx.lifecycle.LiveData
import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProductRepository(private val dao: ProductDAO) {

    val products = dao.getAllProducts()

    val users = dao.getAllUsers()

//    val cart = getCurrentCart(getMaxOrderID())

    val orders = dao.getAllOrders()

    companion object {
        const val TAG = "ProductRepository"
    }

    fun insertUser(userTable: UserTable) : Maybe<Long> = dao.insertUser(userTable)

    fun insertOrder(order: OrderTable) : Maybe<Long> = dao.insertOrder(order)

    fun getUserById(id: Long) {
        val temp = dao.getUserById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MaybeObserver<UserTable>{
                override fun onSuccess(t: UserTable) {
                    Log.d(TAG, "onSuccess $t")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d(TAG, "onSubscribe $d")
                }

                override fun onError(e: Throwable) {
                    Log.d(TAG, "onError $e")
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }
            })
    }

    fun insertCart(orderDetailsTable: OrderDetailsTable) : Maybe<Long> = dao.insertCart(orderDetailsTable)

    suspend fun insertProduct(product: ProductTable) {
        dao.insertProduct(product)
    }

    suspend fun update(product: ProductTable) {
        dao.update(product)
    }

    suspend fun deleteProduct(product: ProductTable) {
        dao.delete(product)
    }

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    fun getOrderTableRowCount() : Single<Long> = dao.getOrderTableRowCount()

    fun getMaxOrderTableId() : Single<Long> = dao.getMaxOrderTableId()

    fun getAllCart()  = dao.getAllCart2()

}