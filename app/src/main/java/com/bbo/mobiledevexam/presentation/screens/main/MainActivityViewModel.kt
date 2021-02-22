package com.bbo.mobiledevexam.presentation.screens.main

import androidx.databinding.Observable
import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.db.ProductRepository

class MainActivityViewModel(private val repository: ProductRepository): ViewModel(), Observable {

    val products = repository.products

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}