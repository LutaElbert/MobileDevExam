package com.bbo.mobiledevexam.presentation.screens.cart

import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.db.ProductRepository

class CartViewModel(repository: ProductRepository) : ViewModel() {

    val cart = repository.products
}