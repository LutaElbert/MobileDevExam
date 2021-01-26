package com.bbo.mobiledevexam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.databinding.ItemProductCategoryBinding
import com.bbo.mobiledevexam.enum.CustomFont
import com.bbo.mobiledevexam.model.Category
import com.bbo.mobiledevexam.model.Product
import com.bbo.mobiledevexam.presentation.product.ProductViewModel
import com.bbo.mobiledevexam.util.extension.getCustomFont

class ProductCategoryAdapter(data: MutableList<Product>?, val onClick: ((category: List<Category>?) -> Unit))
    : RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {

    private var categories: List<Category>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }
        get() = field

    init {
        categories = data?.distinctBy { it.category }?.flatMap {
            val category = mutableListOf<Category>()
            if (it.id == ProductViewModel.CATEGORY_ALL)
                category.add(Category(it.id, it.category, true))
            else
                category.add(Category(it.id, it.category.plus("s"), false))
            category
        }
    }

    inner class ViewHolder(val binding: ItemProductCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun init(category: Category) {
            binding.apply {
                textCategory.text = category.name
                textCategory.typeface = root.context.getCustomFont(CustomFont.MontserratExtraBold)

                if(category.isSelected) {
                    itemView.setBackgroundResource(R.drawable.outline_active)
                }
                else {
                    itemView.setBackgroundResource(0)
                }

                itemView.setOnClickListener {
                    if(category.id == ProductViewModel.CATEGORY_ALL)
                        clearSelection()

                    category.isSelected = !category.isSelected
                    notifyDataSetChanged()

                    onClick.invoke(categories)
                }
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductCategoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return categories?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        categories?.let {
            holder.init(it[position])
        }

    }

    private fun clearSelection() {
        categories?.forEach { it.isSelected = false }
    }
}