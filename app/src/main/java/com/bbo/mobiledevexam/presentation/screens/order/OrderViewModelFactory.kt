package com.bbo.mobiledevexam.presentation.screens.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bbo.mobiledevexam.db.ProductRepository
import java.lang.IllegalArgumentException

class OrderViewModelFactory(val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OrderViewModel::class.java)) {
            return OrderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}