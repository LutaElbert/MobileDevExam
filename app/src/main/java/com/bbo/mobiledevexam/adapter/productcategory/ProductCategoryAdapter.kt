package com.bbo.mobiledevexam.adapter.productcategory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bbo.mobiledevexam.databinding.ItemProductCategoryBinding
import com.bbo.mobiledevexam.model.Category

class ProductCategoryAdapter(val onClick: ((category: Category) -> Unit))
    : ListAdapter<Category, ProductCategoryAdapter.ViewHolder>(
    CategoryDiffCallback()
) {

    inner class ViewHolder(val binding: ItemProductCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun init(category: Category) {
            binding.apply {
                adapterListener = this@ProductCategoryAdapter
                categoryItem = category
                position = layoutPosition
                executePendingBindings()

            }
        }
    }

    fun onClick(category: Category, position: Int) {
        category.isSelected = !category.isSelected
        notifyItemChanged(position)
        onClick.invoke(category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductCategoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(getItem(position))
    }
}

internal class CategoryDiffCallback: DiffUtil.ItemCallback<Category>(){
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}