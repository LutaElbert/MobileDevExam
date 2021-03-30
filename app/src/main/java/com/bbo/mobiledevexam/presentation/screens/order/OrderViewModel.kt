package com.bbo.mobiledevexam.presentation.screens.order

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.MobileDevExamApplication
import com.bbo.mobiledevexam.db.OrderTable
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.network.response.JsonResponse
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class OrderViewModel(val application: Application, val repository: ProductRepository) : ViewModel() {

    var callback : Callback? = null

    private val compositeDisposable = CompositeDisposable()

    private var gsonHelper: JsonResponse? = null

    private var mobileDevExamApplication: MobileDevExamApplication? = null
        set(value) {
            value?.repository = repository
            field = value

            gsonHelper = value?.productResponse
        }

    init {
        mobileDevExamApplication = (application as MobileDevExamApplication)
    }

    override fun onCleared() {
        super.onCleared()
        callback = null
        compositeDisposable.clear()
    }

    fun getOrderNumber(onSuccess: (orderId: Long) -> Unit) {
        repository.getMaxOrderTableId()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Long>{
                override fun onSuccess(t: Long) {
                    saveOrder(t)
                    repository.deleteAllFromCart()
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

    fun onClickReturnToProducts() {
        callback?.onClickReturnToProducts()
    }

    private fun saveOrder(orderId: Long) {
        repository.findOrders(orderId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<OrderTable>>{
                override fun onSuccess(t: List<OrderTable>) {
                    gsonHelper?.saveOrder(orderId, t)
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    callback?.onError(e.message)
                }
            })
    }

    interface Callback {
        fun onClickReturnToProducts()
        fun onError(message: String?)
    }

}