package com.bbo.mobiledevexam.presentation.screens.order

import android.app.Application
import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.MobileDevExamApplication
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

    private var gsonHelper: JsonResponse = (application as MobileDevExamApplication).productResponse

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
                    onSuccess.invoke(t)
                    repository.deleteAllFromCart()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    fun onClickReturnToProducts() {
        saveOrder()

        callback?.onClickReturnToProducts()
    }

    private fun saveOrder() {
        gsonHelper.saveOrder(repository.products.value)
    }

    interface Callback {
        fun onClickReturnToProducts()
        fun onError(message: String?)
    }

}