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


fun MutableList<Product>.getCategories(): MutableList<Category>? {
    return sortProductCategory().filterProductCategory()?.toMutableList()
}

fun MutableList<Category>.toggleHeader(category: Category) {
    forEach { item -> item.isSelected = item.id == category.id }
}

fun MutableList<Category>.toggleItem(category: Category) {
    forEachIndexed { index, _category ->
        if (index == 0) {
            _category.isSelected = false
        } else {
            if(_category.id == category.id && !_category.isSelected)
                _category.isSelected = true
            else if (_category.id == category.id && _category.isSelected)
                _category.isSelected = false
        }
    }
}


private fun MutableList<Product>.filterProductCategory(): List<Category>? {
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

private fun MutableList<Product>.sortProductCategory(): MutableList<Product> {
    return this.apply {
        sortByDescending { it.price }
        add(0, Product(id = CATEGORY_ALL, category = CATEGORY_ALL))
    }
}