package com.bbo.mobiledevexam.presentation.screens.order

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.bbo.mobiledevexam.MobileDevExamApplication
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.network.response.JsonResponse
import com.google.gson.Gson
import java.io.FileWriter
import java.io.IOException

class OrderViewModel(val application: Application, val repository: ProductRepository) : ViewModel() {

    var callback : Callback? = null
    var gson = Gson()

    private var gsonHelper: JsonResponse = (application as MobileDevExamApplication).productResponse

    override fun onCleared() {
        super.onCleared()
        callback = null
    }

    fun onClickReturnToProducts() {
        saveOrder()

        callback?.onClickReturnToProducts()
    }

    private fun saveOrder() {
        gsonHelper.saveOrder(repository.products.value)
    }

    interface Callback {
        fun onClickReturnToProducts()
    }

}