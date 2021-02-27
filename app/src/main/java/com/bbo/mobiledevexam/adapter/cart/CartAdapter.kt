package com.bbo.mobiledevexam.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bbo.mobiledevexam.databinding.ItemCartBinding
import com.bbo.mobiledevexam.databinding.ItemCartFooterBinding
import com.bbo.mobiledevexam.db.CartTable

class CartAdapter(var listener: Listener): ListAdapter<CartTable, RecyclerView.ViewHolder>(ProductDiffCallback) {

    private var total: Double? = 0.0

    var cartList: MutableList<CartTable>? = null
        set(value) {
            field = value
            submitList(value)

            total = value?.sumByDouble { it.productPrice ?: 0.0 }

            notifyDataSetChanged()
        }

    inner class ViewHolderList(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {

        fun init(cart: CartTable, listener: Listener) {
            binding.apply {
                this.product = cart
                this.listener = listener
                executePendingBindings()
            }
        }

    }

    inner class ViewHolderFooter(private val binding: ItemCartFooterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun init(listener: Listener) {
            binding.apply {
                sum = total
            }
        }

    }

    override fun submitList(list: MutableList<CartTable>?) {
        list?.add(CartTable(productId = ""))
        super.submitList(list)
    }

    override fun getItemViewType(position: Int): Int {
        if (isFooter(position)) return ViewType.TYPE_FOOTER.ordinal
        return ViewType.TYPE_LIST.ordinal
    }

    private fun isFooter(position: Int): Boolean = position == itemCount.minus(1)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            ViewType.TYPE_LIST.ordinal -> {
                val binding = ItemCartBinding.inflate(layoutInflater, parent, false)
                ViewHolderList(binding)
            } else -> {
                val binding = ItemCartFooterBinding.inflate(layoutInflater, parent, false)
                ViewHolderFooter(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder.itemViewType) {
            ViewType.TYPE_LIST.ordinal -> {
                (holder as ViewHolderList).init(getItem(position), listener)
            }
            else -> {
                (holder as ViewHolderFooter).init(listener)
            }
        }
    }

    class Listener(val listener: (id: String) -> Unit) {
        fun onClick(product: CartTable) = listener(product.productId)
    }

    companion object {

        object ProductDiffCallback: DiffUtil.ItemCallback<CartTable>() {
            override fun areItemsTheSame(oldItem: CartTable, newItem: CartTable): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(oldItem: CartTable, newItem: CartTable): Boolean {
                return oldItem == newItem
            }
        }

        enum class ViewType {
            TYPE_LIST,
            TYPE_FOOTER
        }
    }
}