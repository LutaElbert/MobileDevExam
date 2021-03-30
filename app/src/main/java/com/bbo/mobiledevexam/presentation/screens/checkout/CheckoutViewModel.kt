package com.bbo.mobiledevexam.presentation.screens.checkout

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.db.OrderTable
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.db.UserTable
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class CheckoutViewModel(val repository: ProductRepository) : ViewModel(), Observable {

    private val cart = repository.products

    private val compositeDisposable = CompositeDisposable()

    private val sum = cart.value?.sumByDouble { it.productPrice ?: Double.NaN}

    var callback : Callback? = null

    val textSum = "Pay $".plus(sum?.toInt().toString())

    @Bindable
    var editTextName = MutableLiveData<String>()

    @Bindable
    var editTextEmail = MutableLiveData<String>()

    fun onPay() {
        if (invalidInputs()) {
           return
        }

        insertUser()
    }

    private fun insertUser() {
        if (editTextEmail.value.isNullOrEmpty() || editTextName.value.isNullOrEmpty()) return

        val user = UserTable(
            UUID.randomUUID().toString(),
            editTextEmail.value.toString(),
            editTextName.value.toString()
        )

        insertUser(user)
    }

    private fun insertUser(user: UserTable) {
        repository.insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Long>{
                override fun onSuccess(t: Long) {
                    navigateToOrderConfirmation()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    callback?.onError(e.message)
                }
            })
    }

    private fun navigateToOrderConfirmation() {
        getOrderRowCount{rowCount ->
            if (rowCount < 1)
                insertOrder(getOrders(OrderTable.START_ID))
            else {
                getOrderMaxId {maxId ->
                    insertOrder(getOrders(maxId.inc()))
                }
            }
        }
    }

    private fun getOrders(id: Long): List<OrderTable> {
        val cart = cart.value
        val list = mutableListOf<OrderTable>()

        cart?.forEach {
            list.add(OrderTable(orderId = id, userEmail = editTextEmail.value.toString(), products = it))
        }

        return list
    }

    private fun insertOrder(orders: List<OrderTable>) {
        repository.insertAllOrder(orders)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Long>> {
                override fun onSuccess(t: List<Long>) {
                    callback?.onPay()
                }

                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    callback?.onError(e.message)
                }
            })
    }

    private fun getOrderMaxId(onSuccess: (Long) -> Unit) = repository.getMaxOrderTableId()
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

    private fun getOrderRowCount(onSuccess: (Long) -> Unit) = repository.getOrderTableRowCount()
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


    private fun invalidInputs() : Boolean = !validInputs()

    private fun validInputs(): Boolean {
        val isAgreed = callback?.isAgreedToTermAndConditions() ?: false
        return isValidInputs() && isAgreed
    }

    private fun isValidInputs(): Boolean = !editTextName.value.isNullOrEmpty() && !editTextEmail.value.isNullOrEmpty()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun onCleared() {
        super.onCleared()
        callback = null
        compositeDisposable.clear()
    }

    interface Callback {
        fun isAgreedToTermAndConditions() : Boolean
        fun onError(message: String?)
        fun onPay()
    }

}