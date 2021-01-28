package com.bbo.mobiledevexam.presentation.product

import android.app.Application
import android.graphics.Typeface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.model.*
import com.bbo.mobiledevexam.util.constant.Constants
import com.bbo.mobiledevexam.util.extension.getCustomFont
import com.bbo.mobiledevexam.util.extension.getLocalJson
import com.google.gson.Gson
import kotlinx.coroutines.*

class ProductViewModel(var application: Application) : ViewModel() {

    private var gson = Gson()

    private var jsonResult: String? = null

    private var viewModelJob = Job()

    private var coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _productListResponse = MutableLiveData<ProductList>()
    val productResponse: LiveData<ProductList>?
        get() = _productListResponse

    var productItemList = Transformations.map(requireNotNull(productResponse)) { productList ->
        val list = mutableListOf<ProductItemList>()
        val products = _productListResponse.value?.products?.toMutableList()
        products?.forEach {
            list.add(ProductItemList(
                id = it.id,
                name = it.name,
                category = it.category,
                price = it.price,
                color = it.color,
                quantity = 0
            ))
        }
        list
    }

    var categories = Transformations.map(requireNotNull(productResponse)) { productList ->
        //add all to the category
        val products = _productListResponse.value?.products?.toMutableList()
        products?.apply {
            sortByDescending { it.price }
            add(0, Product(id = CATEGORY_ALL, category = CATEGORY_ALL))
        }

        //filter and concatenate category list
        products?.distinctBy { it.category }?.flatMap {
            val category = mutableListOf<Category>()
            if (it.id == CATEGORY_ALL)
                category.add(Category(it.id, it.category, true))
            else
                category.add(Category(it.id, it.category.plus("s"), false))
            category
        }
    }

    companion object {
        const val TAG = "ProductViewModel"
        const val CATEGORY_ALL = "All"
    }

    init {
        jsonResult = application.getLocalJson(Constants.JSON_FILE_NAME)
        _productListResponse.value = gson.fromJson(jsonResult, ProductList::class.java)
//        coroutineScope.launch {
//            withContext(Dispatchers.IO) {
//
//            }
//        }
    }

    fun updateProduct(category: Category) {
        categories.value?.find { it.id == category.id }?.apply {
            isSelected = category.isSelected
        }
    }

    fun setScreenTitleTypeFace(): Typeface {
        return application.applicationContext.getCustomFont(CustomFont.MontserratExtraBold)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

}