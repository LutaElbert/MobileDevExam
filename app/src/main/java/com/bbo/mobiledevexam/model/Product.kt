package com.bbo.mobiledevexam.model

import com.google.gson.annotations.SerializedName

data class Product(
    val id: String? = null,
    val name: String? = null,
    val category: String? = null,
    val price: Float? = null,
    @SerializedName("bgColor")
    val color: String? = null
)