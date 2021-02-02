package com.bbo.mobiledevexam.presentation.product

import android.app.Application
import android.graphics.Typeface
import androidx.lifecycle.*
import com.bbo.mobiledevexam.MobileDevExamApplication
import com.bbo.mobiledevexam.model.*
import com.bbo.mobiledevexam.util.constant.Constants
import com.bbo.mobiledevexam.util.extension.getCustomFont
import com.bbo.mobiledevexam.util.extension.getLocalJson
import com.google.gson.Gson
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

    var categories = Transformations.map(requireNotNull(productResponse)) { productList ->
        val products = productList.products?.toMutableList()
        products?.getCategories()
    }

    companion object {
        const val TAG = "ProductViewModel"

    }

    init {
        val productResponse = (application as MobileDevExamApplication).productResponse
        _productListResponse.value = productResponse.getProductList()
    }

    fun displayProductList(category: Category) {
        val products = productResponse?.value?.products?.toMutableList()?.getProductItemList()
        val result = mutableListOf<ProductItemList>()
        products?.forEach {
            if(it.category == category.name && category.isSelected){
                result.add(it)
            }

        }

        _productItemList.value = result
    }

    fun setScreenTitleTypeFace(): Typeface {
        return application.applicationContext.getCustomFont(CustomFont.MontserratExtraBold)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}