package com.bbo.mobiledevexam.presentation.screens.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bbo.mobiledevexam.adapter.cart.CartAdapter
import com.bbo.mobiledevexam.adapter.productlist.ProductListAdapter
import com.bbo.mobiledevexam.databinding.FragmentCartBinding
import com.bbo.mobiledevexam.databinding.ItemCartBinding
import com.bbo.mobiledevexam.db.ProductDatabase
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.model.ProductItemList

class CartFragment : Fragment() {

    private lateinit var binding: FragmentCartBinding

    private lateinit var viewModel: CartViewModel

    private lateinit var adapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this, CartViewModelFactory(getDatabaseInstance())).get(CartViewModel::class.java)

        binding = FragmentCartBinding.inflate(layoutInflater, container, false)

        val adapter = CartAdapter(CartAdapter.Listener {

        })

        viewModel.cart.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        binding.recycler.adapter = adapter

        return binding.root
    }

    private fun getDatabaseInstance() : ProductRepository {
        val dao = ProductDatabase.getInstance(requireNotNull(context)).productDAO
        return ProductRepository(dao)
    }

}