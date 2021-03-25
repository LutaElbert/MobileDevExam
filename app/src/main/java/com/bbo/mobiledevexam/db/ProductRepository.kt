package com.bbo.mobiledevexam.db

import android.util.Log
import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ProductRepository(private val dao: ProductDAO) {

    val products = dao.getAllProducts()

    val users = dao.getAllUsers()

    fun insertUser(userTable: UserTable) : Maybe<Long> = dao.insertUser(userTable)

    fun getUserById(id: Long) {
        val temp = dao.getUserById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MaybeObserver<UserTable>{
                override fun onSuccess(t: UserTable) {
                    Log.d("qwe", "qwe get onSuccess $t")
                }

                override fun onSubscribe(d: Disposable) {
                    Log.d("qwe", "qwe get onSubscribe $d")
                }

                override fun onError(e: Throwable) {
                    Log.d("qwe", "qwe get onError $e")
                }

                override fun onComplete() {
                    Log.d("qwe", "qwe get onComplete")
                }
            })
    }

    suspend fun insertCart(orderDetailsTable: OrderDetailsTable) {
        dao.insertCart(orderDetailsTable)
    }

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
}