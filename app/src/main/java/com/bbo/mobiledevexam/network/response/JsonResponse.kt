package com.bbo.mobiledevexam.network.response

import android.content.Context
import android.util.Log
import com.bbo.mobiledevexam.db.CartTable
import com.bbo.mobiledevexam.db.OrderTable
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.model.ProductList
import com.bbo.mobiledevexam.util.constant.Constants
import com.bbo.mobiledevexam.util.extension.getLocalJson
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class JsonResponse(val context: Context, val repository: ProductRepository?) : JsonInterface {

    private var gson = Gson()

    private val path = context.cacheDir.absolutePath

    private val response = context.getLocalJson(Constants.JSON_FILE_NAME)

    companion object {
        const val TAG = "JsonResponse"
    }

    override fun getProductList(): ProductList = gson.fromJson(response, ProductList::class.java)

    fun saveOrder(orderId: Long, orders: List<OrderTable>?) {
        orders?.let {

            try {
                val writer = FileWriter("$path/order_$orderId.json")
                gson.toJson(it, writer)
                writer.close()

                readJSONFromFile(orderId)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun readJSONFromFile(orderId: Long) {
        val gsonBuilder = GsonBuilder().create()
        val path = context.cacheDir.absolutePath
        val br = BufferedReader(FileReader("$path/order_$orderId.json"))

        val temp = gsonBuilder.fromJson(br, Array<OrderTable>::class.java)

        temp.forEach {
            Log.d(TAG, "display order$it")
        }
    }
}