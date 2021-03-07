package com.bbo.mobiledevexam.presentation.screens.checkout

import android.util.Log
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.db.ProductRepository

class CheckoutViewModel(val repository: ProductRepository) : ViewModel(), Observable {

    private val cart = repository.products

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


    }

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

    interface Callback {
        fun isAgreedToTermAndConditions() : Boolean
    }

}