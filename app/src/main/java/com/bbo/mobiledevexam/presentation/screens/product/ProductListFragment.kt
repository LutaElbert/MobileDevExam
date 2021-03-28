package com.bbo.mobiledevexam.presentation.screens.product

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.adapter.productcategory.ProductCategoryAdapter
import com.bbo.mobiledevexam.adapter.productlist.ProductListAdapter
import com.bbo.mobiledevexam.adapter.productlist.setBackgroundTint
import com.bbo.mobiledevexam.databinding.FragmentProductListBinding
import com.bbo.mobiledevexam.databinding.SnackbarItemAddedBinding
import com.bbo.mobiledevexam.db.ProductDatabase
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.presentation.screens.main.MainActivity
import com.bbo.mobiledevexam.util.extension.makeGone
import com.bbo.mobiledevexam.util.extension.makeVisible
import com.bbo.mobiledevexam.util.extension.messageFormat
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class ProductListFragment : Fragment(), ProductViewModel.Callback {

    private lateinit var binding: FragmentProductListBinding

    private lateinit var viewModelFactory: ProductViewModelFactory

    private lateinit var viewModel: ProductViewModel

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentProductListBinding.inflate(layoutInflater, container, false)

        mainActivity = activity as MainActivity

        viewModelFactory = ProductViewModelFactory(requireNotNull(activity).application, mainActivity.repository)

        viewModel = ViewModelProvider(this, viewModelFactory).get(ProductViewModel::class.java)
        viewModel.callback = this

        binding.productScreenViewModel = viewModel
        binding.lifecycleOwner = this

        setupObservers()

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

    private fun setupObservers() {
        viewModel.cart.observe(viewLifecycleOwner, Observer { list ->

            mainActivity.image_badge.apply {
                if (list.isNotEmpty()) makeVisible() else makeGone()
            }

            mainActivity.text_badge.apply {
                text = list.size.toString()
            }

        })
    }

    private fun insertToCart(it: String?) {
        viewModel.insert(requireNotNull(it)) {
            displayAddedItemMessage(it.name, it.color ?: "")
        }
    }

    private fun displayAddedItemMessage(msg: String?, hexColor: String) {
        val snackbarPadding = resources.getDimension(R.dimen.dimens_16dp).toInt()

        val snackbar = Snackbar.make(binding.root, "", Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.TRANSPARENT)

        val snackbarItemAddedBinding = SnackbarItemAddedBinding.inflate(layoutInflater)
        snackbarItemAddedBinding.apply {

            textItemAdded.text = context?.messageFormat(msg)
            imageClose.setOnClickListener {
                snackbar.dismiss()
            }

            containerBackground.setBackgroundTint(hexColor)
        }

        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
        snackbarLayout.apply {
            setPadding(snackbarPadding, snackbarPadding, snackbarPadding, snackbarPadding)
            addView(snackbarItemAddedBinding.root)
        }

        snackbar.show()
    }

    override fun onError(message: String?) {
        message?.let{
            mainActivity.onError(it)
        }
    }

}