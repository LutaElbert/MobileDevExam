package com.bbo.mobiledevexam.presentation.screens.product

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.adapter.productcategory.ProductCategoryAdapter
import com.bbo.mobiledevexam.adapter.productlist.ProductListAdapter
import com.bbo.mobiledevexam.databinding.FragmentProductListBinding
import com.bbo.mobiledevexam.databinding.SnackbarItemAddedBinding
import com.bbo.mobiledevexam.db.ProductDatabase
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.util.extension.messageFormat
import com.google.android.material.snackbar.Snackbar

class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding

    private lateinit var viewModel: ProductViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentProductListBinding.inflate(layoutInflater, container, false)

        val dao = ProductDatabase.getInstance(requireNotNull(context)).productDAO
        val repository = ProductRepository(dao)
        val factory = ProductViewModelFactory(requireNotNull(this.activity).application, repository)

        viewModel = ViewModelProvider(this, factory).get(ProductViewModel::class.java)

        binding.productScreenViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            val productListAdapter = ProductListAdapter(ProductListAdapter.Listener{
                insertToCart(it)
            })

            val productCategoryAdapter = ProductCategoryAdapter{
                viewModel.displayProductList(it)
            }

            recyclerCategory.apply {
                adapter = productCategoryAdapter
            }

            recyclerSubitem.apply {
                adapter = productListAdapter
            }

            viewModel.categories.observe(viewLifecycleOwner, Observer {
                it?.let {
                    productCategoryAdapter.submitList(it)
                }
            })

            viewModel.productItemList.observe(viewLifecycleOwner, Observer {
                it?.let {
                    productListAdapter.submitList(it)
                }
            })
        }
    }

    private fun insertToCart(it: String?) {
        viewModel.insert(it) {
            displayAddedItemMessage(it.name, it.color ?: "")
        }
    }

    private fun displayAddedItemMessage(msg: String?, itemColor: String) {
        val snackbarPadding = resources.getDimension(R.dimen.dimens_16dp).toInt()

        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)

        val snackbarItemAddedBinding = SnackbarItemAddedBinding.inflate(layoutInflater)
        snackbarItemAddedBinding.apply {
            val drawable = containerBackground.background

            textItemAdded.text = context?.messageFormat(msg)
            imageClose.setOnClickListener {
                snackbar.dismiss()
            }

            DrawableCompat.setTint(drawable, Color.parseColor(itemColor))
        }

        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        snackbarLayout.apply {
            setPadding(snackbarPadding, snackbarPadding, snackbarPadding, snackbarPadding)
            addView(snackbarItemAddedBinding.root)
        }

        snackbar.show()
    }

}