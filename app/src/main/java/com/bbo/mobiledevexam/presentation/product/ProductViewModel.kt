package com.bbo.mobiledevexam.presentation.product

import android.app.Application
import android.graphics.Typeface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.enum.CustomFont
import com.bbo.mobiledevexam.model.Product
import com.bbo.mobiledevexam.model.ProductList
import com.bbo.mobiledevexam.util.constant.Constants
import com.bbo.mobiledevexam.util.extension.getCustomFont
import com.bbo.mobiledevexam.util.extension.getLocalJson
import com.google.gson.Gson
import kotlinx.coroutines.*

class ProductViewModel(var application: Application) : ViewModel() {

    private var gson = Gson()

    private var productList: ProductList? = null

    private var jsonResult: String? = null

    private var viewModelJob = Job()

    private var coroutineScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var _productList = MutableLiveData<ProductList>()

//    private val productList: LiveData<ProductList>?
//        get() = _productList
//
//    var products = Transformations.map(requireNotNull(productList)) {
//        it.products?.apply {
//            sortByDescending { it.price }
//            add(0, Product(id = CATEGORY_ALL, category = CATEGORY_ALL))
//        }
//    }

    companion object {
        const val TAG = "ProductViewModel"
        const val CATEGORY_ALL = "All"
    }

    init {

        jsonResult = application.getLocalJson(Constants.JSON_FILE_NAME)
        productList = gson.fromJson(jsonResult, ProductList::class.java)
//        coroutineScope.launch {
//            withContext(Dispatchers.IO) {
//
//            }
//        }
    }

    fun getProducts() : MutableList<Product>? {
        return productList?.products?.apply {
            sortByDescending { it.price }
            add(0, Product(id = CATEGORY_ALL, category = CATEGORY_ALL))
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