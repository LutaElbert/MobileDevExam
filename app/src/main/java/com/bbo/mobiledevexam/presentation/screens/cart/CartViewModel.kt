package com.bbo.mobiledevexam.presentation.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bbo.mobiledevexam.db.CartTable
import com.bbo.mobiledevexam.db.ProductRepository
import kotlinx.coroutines.launch

class CartViewModel(val repository: ProductRepository) : ViewModel() {

    val cart = repository.products

    fun isEmptyCart() = cart.value?.isEmpty() ?: true

    fun deleteItem(id: String?) {
        val item = cart.value?.find { it.productId == id } ?: return
        delete(item)
    }

    private fun delete(cart: CartTable) = viewModelScope.launch {
        repository.deleteCart(cart)
    }
}