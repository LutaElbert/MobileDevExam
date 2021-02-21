package com.bbo.mobiledevexam.model

data class ProductItemList(override var id: String? = null,
                           override var name: String? = null,
                           override var category: String? = null,
                           override var price: Double? = null,
                           override var color: String? = null,
                           var quantity: Int? = 0): Product() {

    fun incrementQuantity() {
        quantity = quantity?.plus(1)
        println(quantity)
    }

    fun decrementeQuantity() {
        quantity = when (quantity?.minus(1)?.compareTo(0)) {
           -1 -> 0
           else -> quantity?.minus(1)
       }

        println(quantity)
    }
}