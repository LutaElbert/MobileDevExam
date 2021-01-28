package com.bbo.mobiledevexam.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.databinding.ItemProductCategoryBinding
import com.bbo.mobiledevexam.model.CustomFont
import com.bbo.mobiledevexam.model.Category
import com.bbo.mobiledevexam.util.extension.getCustomFont
import com.bbo.mobiledevexam.util.extension.makeGone
import com.bbo.mobiledevexam.util.extension.makeVisible

class ProductCategoryAdapter
    : ListAdapter<Category, ProductCategoryAdapter.ViewHolder>(CategoryDiffCallback()) {

    inner class ViewHolder(val binding: ItemProductCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun init(category: Category) {
            binding.apply {
                textCategory.text = category.name
                textCategory.typeface = root.context.getCustomFont(CustomFont.MontserratExtraBold)

                if(category.isSelected) {
                    itemView.setBackgroundResource(R.drawable.outline_active)
                    imageCross.makeVisible()
                }
                else {
                    itemView.setBackgroundResource(0)
                    imageCross.makeGone()
                }

                itemView.setOnClickListener {
                    category.isSelected = !category.isSelected
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductCategoryBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

//    override fun getItemCount(): Int {
//        return categories?.size ?: 0
//    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(getItem(position))
    }

//    private fun clearSelection() {
//        categories?.forEach { it.isSelected = false }
//    }
}

internal class CategoryDiffCallback: DiffUtil.ItemCallback<Category>(){
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}