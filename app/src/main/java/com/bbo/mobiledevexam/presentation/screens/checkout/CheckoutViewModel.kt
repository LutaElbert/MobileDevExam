package com.bbo.mobiledevexam.presentation.screens.checkout

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.db.UserTable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class CheckoutViewModel(val repository: ProductRepository) : ViewModel(), Observable {

    private val cart = repository.products

    private val sum = cart.value?.sumByDouble { it.productPrice ?: Double.NaN}

    private val compositeDisposable = CompositeDisposable()

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
        val disposable = repository.insertUser(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{navigateToOrderConfirmation()}

        compositeDisposable.add(disposable)
    }

    private fun navigateToOrderConfirmation() {
        callback?.onPay()
    }

    private fun invalidInputs() : Boolean = !validInputs()

    private fun validInputs(): Boolean {
        val isAgreed = callback?.isAgreedToTermAndConditions() ?: false
        return isValidInputs() && isAgreed
    }

    private fun isValidInputs(): Boolean = !editTextName.value.isNullOrEmpty() && !editTextEmail.value.isNullOrEmpty()

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}

    override fun onCleared() {
        super.onCleared()
        callback = null
        compositeDisposable.clear()
    }

    interface Callback {
        fun isAgreedToTermAndConditions() : Boolean
        fun onPay()
    }

    companion object {
        const val TAG = "CheckoutViewModel"
    }

}