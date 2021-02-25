package com.bbo.mobiledevexam.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bbo.mobiledevexam.databinding.ItemCartBinding
import com.bbo.mobiledevexam.db.CartTable
import com.bbo.mobiledevexam.model.ProductItemList

class CartAdapter( var listener: Listener): ListAdapter<CartTable, CartAdapter.ViewHolder>(ProductDiffCallback) {

    companion object ProductDiffCallback: DiffUtil.ItemCallback<CartTable>() {
        override fun areItemsTheSame(oldItem: CartTable, newItem: CartTable): Boolean {
            return oldItem.productId == newItem.productId
        }

        override fun areContentsTheSame(oldItem: CartTable, newItem: CartTable): Boolean {
            return oldItem == newItem
        }

    }

    inner class ViewHolder(val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {

        fun init(cart: CartTable, listener: Listener) {
            binding.apply {
                this.product = cart
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

    class Listener(val onClickListener: (id: String) -> Unit) {
        fun onClick(product: CartTable) = onClickListener(product.productId)
    }
}