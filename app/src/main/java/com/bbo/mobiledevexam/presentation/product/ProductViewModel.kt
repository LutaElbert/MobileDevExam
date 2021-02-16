package com.bbo.mobiledevexam.presentation.product

import android.app.Application
import androidx.lifecycle.*
import com.bbo.mobiledevexam.MobileDevExamApplication
import com.bbo.mobiledevexam.model.*
import kotlinx.coroutines.*

class ProductViewModel(var application: Application) : ViewModel() {

    private var viewModelJob = Job()

    private var coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}