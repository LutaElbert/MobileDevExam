package com.bbo.mobiledevexam.presentation.screens.checkout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bbo.mobiledevexam.db.ProductRepository
import java.lang.IllegalArgumentException

class CheckoutViewModelFactory(val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CheckoutViewModel::class.java)) {
            return CheckoutViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}