package com.bbo.mobiledevexam.adapter.productlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bbo.mobiledevexam.databinding.ItemProductBinding
import com.bbo.mobiledevexam.model.ProductItemList

class ProductListAdapter(val listener: Listener): ListAdapter<ProductItemList, ProductListAdapter.ViewHolder>(ProductDiffCallback) {

    companion object ProductDiffCallback: DiffUtil.ItemCallback<ProductItemList>() {
        override fun areItemsTheSame(oldItem: ProductItemList, newItem: ProductItemList): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductItemList, newItem: ProductItemList): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root) {

        fun init(product: ProductItemList, listener: Listener) {
            binding.apply {
                this.product = product
                this.listener = listener
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(getItem(position), listener)
    }

    class Listener(val onClick: (id: String?) -> Unit) {
        fun onClick(product: ProductItemList) = onClick(product.id)
    }
}