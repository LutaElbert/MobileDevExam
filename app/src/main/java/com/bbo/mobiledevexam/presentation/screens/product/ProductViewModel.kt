package com.bbo.mobiledevexam.presentation.screens.product

import android.app.Application
import android.text.style.StyleSpan
import androidx.databinding.Observable
import androidx.lifecycle.*
import com.bbo.mobiledevexam.MobileDevExamApplication
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.db.CartTable
import com.bbo.mobiledevexam.model.*
import com.bbo.mobiledevexam.util.extension.getCustomFont
import kotlinx.coroutines.*

class ProductViewModel(var application: Application, private val repository: ProductRepository) : ViewModel(), Observable {

    val cart = repository.products

    private var _productListResponse = MutableLiveData<ProductList>()
    val productResponse: LiveData<ProductList>?
        get() = _productListResponse

    private var _productItemList = MutableLiveData<MutableList<ProductItemList>>()
    val productItemList: LiveData<MutableList<ProductItemList>>
        get() = _productItemList

    private var _categories = MutableLiveData<MutableList<Category>>()
    val categories: LiveData<MutableList<Category>>
        get() = _categories

    private var result: MutableList<ProductItemList>? = mutableListOf()

    companion object {
        const val TAG = "ProductViewModel"
    }

    init {
        val productResponse = (application as MobileDevExamApplication).productResponse
        _productListResponse.value = productResponse.getProductList()
        _productItemList.value = productResponse.getProductList().products?.toMutableList()?.getProductItemList()
        _categories.value = productResponse.getProductList().products?.toMutableList()?.getCategories()
    }

    fun displayProductList(category: Category) {
        val products = productResponse?.value?.products?.toMutableList()?.getProductItemList()

        if(category.name == "All") {
            _categories.value?.let {
                it.toggleHeader(category)
            }

            _productItemList.value = products
            result = null
            return
        } else {

            _categories.value?.let {

                it.toggleItem(category)

                val noSelection = it.all { !it.isSelected }
                if (noSelection) {
                    it.first().isSelected = true
                    _productItemList.value = products
                    result = null
                    return
                }
            }

            val filterResult = mutableListOf<ProductItemList>()
            products?.forEach {
                if(it.category == category.name){
                    filterResult.add(it)
                }
            }

            result = finalizingResult(category, filterResult)

            _productItemList.value = result
        }
    }

    private fun finalizingResult(category: Category, list: MutableList<ProductItemList>): MutableList<ProductItemList>? {
        return result?.let {
            if (category.isSelected)
                it.plus(list).toMutableList()
            else
                it.minus(list).toMutableList()
        } ?: list
    }

    fun insert(id: String?, onSuccess: ((product: ProductItemList) -> Unit)? = null) {
        val product = getProductById(id)
        product?.let {
            insert(
                CartTable(
                    productId = requireNotNull(it.id),
                    productName = it.name,
                    productCategory = it.category,
                    productColor = it.color,
                    productPrice = it.price
                ))
            {
                onSuccess?.invoke(it)
            }
        }
    }

    private fun insert(cartTable: CartTable, onSuccess: (() -> Unit)? = null) {
        viewModelScope.launch {
            val job = launch {
                repository.insert(cartTable)
            }

            job.join()
            onSuccess?.invoke()
        }
    }

    fun getProductById(id: String?) : ProductItemList? = _productItemList.value?.find { it.id == id }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    override fun onCleared() {
        super.onCleared()
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}