package com.bbo.mobiledevexam.network.response

import android.content.Context
import android.util.Log
import com.bbo.mobiledevexam.db.ProductTable
import com.bbo.mobiledevexam.model.ProductList
import com.bbo.mobiledevexam.util.constant.Constants
import com.bbo.mobiledevexam.util.extension.getLocalJson
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class JsonResponse(val context: Context) : JsonInterface {

    private var gson = Gson()

    private val path = context.cacheDir.absolutePath

    private val response = context.getLocalJson(Constants.JSON_FILE_NAME)

    companion object {
        const val TAG = "JsonResponse"
    }

    override fun getProductList() = gson.fromJson(response, ProductList::class.java)

    fun saveOrder(product: List<ProductTable>?) {
        product?.let {

            try {
                val writer = FileWriter("$path/order_orderId.json")
                val json = gson.toJson(product, writer)
                Log.d("qwe", "qwe $json")
                Log.d("qwe", "qwe1 $writer")
                writer.close()

                readJSONFromFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun readJSONFromFile() {
        val gsonBuilder = GsonBuilder().create()
        val path = context.cacheDir.absolutePath
        val br = BufferedReader(FileReader("$path/order_orderId.json"))

        val temp = gsonBuilder.fromJson(br, Array<ProductTable>::class.java)

        temp.forEach {
            Log.d(TAG, "sample display $it")
        }
    }
}