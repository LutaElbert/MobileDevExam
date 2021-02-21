package com.bbo.mobiledevexam.presentation.screens.product

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bbo.mobiledevexam.db.ProductRepository
import java.lang.IllegalArgumentException

class ProductViewModelFactory(val application: Application, val repository: ProductRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ProductViewModel::class.java)) {
            return ProductViewModel(application = application, repository = repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}