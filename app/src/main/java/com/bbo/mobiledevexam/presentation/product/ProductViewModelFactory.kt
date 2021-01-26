package com.bbo.mobiledevexam.presentation.product

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class ProductViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(application = application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}