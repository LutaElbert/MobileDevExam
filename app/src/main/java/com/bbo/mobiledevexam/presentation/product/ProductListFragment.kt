package com.bbo.mobiledevexam.presentation.product

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bbo.mobiledevexam.adapter.ProductCategoryAdapter
import com.bbo.mobiledevexam.databinding.FragmentProductListBinding
import kotlinx.coroutines.*

class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding

    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentProductListBinding.inflate(layoutInflater, container, false)

        val factory = ProductViewModelFactory(requireNotNull(this.activity).application)
        viewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)

        binding.productScreenViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            val productCategoryAdapter = ProductCategoryAdapter(viewModel.getProducts()){ category ->
            }

            recyclerCategory.apply {
                layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.HORIZONTAL, false)
                adapter = productCategoryAdapter
            }

        }
    }

}