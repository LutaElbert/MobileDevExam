package com.bbo.mobiledevexam.presentation.product

import com.bbo.mobiledevexam.model.Category
import com.bbo.mobiledevexam.model.Product
import com.bbo.mobiledevexam.model.ProductItemList

private const val CATEGORY_ALL = "All"

fun MutableList<Product>.getProductItemList(): MutableList<ProductItemList> {
    val list = mutableListOf<ProductItemList>()

    forEach {
        list.add(
            ProductItemList(
                id = it.id,
                name = it.name,
                category = it.category,
                price = it.price,
                color = it.color,
                quantity = 0
            )
        )
    }

    return list
}


fun MutableList<Product>.getCategories(): List<Category>? {
    return sortProductCategory().filterProductCategory()
}

fun MutableList<Product>.filterProductCategory(): List<Category>? {
    return this.distinctBy { it.category }.flatMap {
        val category = mutableListOf<Category>()

        if (it.id == CATEGORY_ALL) {
            category.add(Category(it.id, it.category, true))
        } else {
            category.add(Category(it.id, it.category, false))
        }

        category
    }
}

fun MutableList<Product>.sortProductCategory(): MutableList<Product> {
    return this.apply {
        sortByDescending { it.price }
        add(0, Product(id = CATEGORY_ALL, category = CATEGORY_ALL))
    }
}