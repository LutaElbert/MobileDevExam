package com.bbo.mobiledevexam.network.response

import com.bbo.mobiledevexam.model.ProductList

interface JsonInterface {
    fun getProductList() : ProductList
}