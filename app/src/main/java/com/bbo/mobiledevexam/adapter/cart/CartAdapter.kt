package com.bbo.mobiledevexam.adapter.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bbo.mobiledevexam.databinding.ItemCartBinding
import com.bbo.mobiledevexam.databinding.ItemCartFooterBinding
import com.bbo.mobiledevexam.db.ProductTable

class CartAdapter : ListAdapter<ProductTable, RecyclerView.ViewHolder>(ProductDiffCallback) {

    var listener: Listener? = null

    private var total: Double? = 0.0

    var productList: MutableList<ProductTable>? = null
        set(value) {
            field = value
            submitList(value)

            total = value?.sumByDouble { it.productPrice ?: 0.0 }

            notifyDataSetChanged()
        }

    inner class ViewHolderList(private val binding: ItemCartBinding) : RecyclerView.ViewHolder(binding.root) {

        fun init(product: ProductTable, listener: Listener?) {
            binding.apply {
                this.product = product
                this.listener = listener
                executePendingBindings()
            }
        }

    }

    inner class ViewHolderFooter(private val binding: ItemCartFooterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun init(listener: Listener?) {
            binding.apply {
                sum = total
                this.listener = listener
            }
        }

    }

    override fun submitList(list: MutableList<ProductTable>?) {
        list?.add(ProductTable(productId = ""))
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

    interface Listener {
        fun onDelete(product: ProductTable)
        fun onBuy()
    }

    companion object {

        object ProductDiffCallback: DiffUtil.ItemCallback<ProductTable>() {
            override fun areItemsTheSame(oldItem: ProductTable, newItem: ProductTable): Boolean {
                return oldItem.productId == newItem.productId
            }

            override fun areContentsTheSame(oldItem: ProductTable, newItem: ProductTable): Boolean {
                return oldItem == newItem
            }
        }

        enum class ViewType {
            TYPE_LIST,
            TYPE_FOOTER
        }
    }
}