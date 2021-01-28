package com.bbo.mobiledevexam.model

import com.google.gson.annotations.SerializedName

open class Product(
    open var id: String? = null,
    open var name: String? = null,
    open var category: String? = null,
    open var price: Double? = null,
    @SerializedName("bgColor")
    open var color: String? = null
)