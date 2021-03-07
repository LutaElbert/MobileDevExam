package com.bbo.mobiledevexam.presentation.screens.order

import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.db.ProductRepository

class OrderViewModel(val repository: ProductRepository) : ViewModel() {

    var callback : Callback? = null

    fun onClickReturnToProducts() {
        callback?.onClickReturnToProducts()
    }

    interface Callback {
        fun onClickReturnToProducts()
    }

}