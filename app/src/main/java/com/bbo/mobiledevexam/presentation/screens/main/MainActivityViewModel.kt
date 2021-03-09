package com.bbo.mobiledevexam.presentation.screens.main

import android.view.View
import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.db.ProductRepository

class MainActivityViewModel(private val repository: ProductRepository): ViewModel(), Observable {

    val products = repository.products

    var callback: Callback? = null

    fun onClickCart() {
        callback?.onClickCart()
    }

    fun onDestroy() {
        callback = null
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    interface Callback {
        fun onClickCart()
    }

}