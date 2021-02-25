package com.bbo.mobiledevexam.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bbo.mobiledevexam.databinding.ItemCartBinding
import com.bbo.mobiledevexam.db.ProductTable
import com.bbo.mobiledevexam.model.ProductItemList

class CartAdapter( var listener: Listener): ListAdapter<ProductTable, CartAdapter.ViewHolder>(ProductDiffCallback) {

    companion object ProductDiffCallback: DiffUtil.ItemCallback<ProductTable>() {
        override fun areItemsTheSame(oldItem: ProductTable, newItem: ProductTable): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: ProductTable, newItem: ProductTable): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {

        fun init(product: ProductTable, listener: Listener) {
            binding.apply {
                this.product = product
                this.listener = listener
                executePendingBindings()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCartBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.init(getItem(position), listener)
    }

    class Listener(val onClick: (id: String?) -> Unit) {
        fun onClick(product: ProductItemList) = onClick(product.id)
    }
}