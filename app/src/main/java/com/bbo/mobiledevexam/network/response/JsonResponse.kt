package com.bbo.mobiledevexam.network.response

import android.content.Context
import com.bbo.mobiledevexam.model.ProductList
import com.bbo.mobiledevexam.util.constant.Constants
import com.bbo.mobiledevexam.util.extension.getLocalJson
import com.google.gson.Gson

class JsonResponse(context: Context) : JsonInterface {

    private var gson = Gson()

    private val response = context.getLocalJson(Constants.JSON_FILE_NAME)

    override fun getProductList() = gson.fromJson(response, ProductList::class.java)
}