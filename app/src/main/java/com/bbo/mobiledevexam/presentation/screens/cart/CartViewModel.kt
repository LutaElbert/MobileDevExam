package com.bbo.mobiledevexam.presentation.screens.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbo.mobiledevexam.db.OrderTable
import com.bbo.mobiledevexam.db.ProductTable
import com.bbo.mobiledevexam.db.ProductRepository
import io.reactivex.MaybeObserver
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import java.time.LocalDate

class CartViewModel(val repository: ProductRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    var callback : Callback? = null

    val cart = repository.products

    fun deleteItem(id: String?) {
        val item = cart.value?.find { it.productId == id } ?: return
        delete(item)
    }

    private fun delete(product: ProductTable) = viewModelScope.launch {
        repository.deleteProduct(product)
    }

    fun insertOrder() {
        getOrderTableRowCount {rowCount ->
            if (rowCount < 1)
                insertOrder(OrderTable(OrderTable.START_ID))
            else
                getMaxOrderTableId{
                    insertOrder(OrderTable(it.inc()))
                }
        }
    }

    private fun insertOrder(order: OrderTable) {
        repository.insertOrder(order)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : MaybeObserver<Long> {
                override fun onSuccess(t: Long) {
                    callback?.onOrderInserted()
                }

                override fun onComplete() {
                    Log.d(TAG, "onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    callback?.onError(e.message)
                }
            })
    }

    private fun getOrderTableRowCount(onSuccess : (id : Long) -> (Unit)) {
        repository.getOrderTableRowCount()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Long> {
                override fun onSuccess(t: Long) {
                    onSuccess.invoke(t)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    callback?.onError(e.message)
                }

            })
    }

    private fun getMaxOrderTableId(onSuccess: (id: Long) -> Unit) {
        repository.getMaxOrderTableId()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Long>{
                override fun onSuccess(t: Long) {
                    onSuccess.invoke(t)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    callback?.onError(e.message)
                }
            })
    }

    override fun onCleared() {
        super.onCleared()
        callback = null
        compositeDisposable.clear()
    }

    interface Callback {
        fun onOrderInserted()
        fun onError(message: String?)
    }

    companion object {
        const val TAG = "CartViewModel"
    }
}