package com.bbo.mobiledevexam.datamodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bbo.mobiledevexam.model.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ProductDataModel {

    private var gson = Gson()

    var jsonResult: String? = null

    val listPersonType = object : TypeToken<List<Product>>() {}.type

    private var _productList = MutableLiveData<MutableList<Product>>()
    val productList : LiveData<MutableList<Product>>?
        get() = _productList

    init {
        _productList = gson.fromJson(jsonResult, listPersonType)
    }

}