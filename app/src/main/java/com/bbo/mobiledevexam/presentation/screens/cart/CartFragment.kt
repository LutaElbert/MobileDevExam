package com.bbo.mobiledevexam.presentation.screens.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.adapter.cart.CartAdapter
import com.bbo.mobiledevexam.databinding.FragmentCartBinding
import com.bbo.mobiledevexam.db.CartTable
import com.bbo.mobiledevexam.presentation.screens.main.MainActivity
import com.bbo.mobiledevexam.util.extension.makeGone
import com.bbo.mobiledevexam.util.extension.makeVisible
import kotlinx.android.synthetic.main.activity_main.*

class CartFragment : Fragment(), CartAdapter.Listener {

    private lateinit var binding: FragmentCartBinding

    private lateinit var viewModel: CartViewModel

    private lateinit var adapter: CartAdapter

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = activity as MainActivity

        viewModel = ViewModelProvider(this, CartViewModelFactory(mainActivity.repository))
            .get(CartViewModel::class.java)

        binding = FragmentCartBinding.inflate(layoutInflater, container, false)

        adapter = CartAdapter()

        adapter.listener = this

        binding.recycler.adapter = adapter

        setupObservers()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.cart.observe(viewLifecycleOwner, Observer {
            it?.let {
                mainActivity.image_badge.apply {
                    if (it.isNotEmpty()) makeVisible() else makeGone()
                }

                mainActivity.text_badge.apply {
                    text = it.size.toString()
                }

                adapter.cartList = it.toMutableList()
            }
        })
    }

    override fun onDelete(product: CartTable) {
        viewModel.deleteItem(product.productId)
    }

    override fun onBuy() {
        if (viewModel.isEmptyCart()) return

        activity?.let {
            it.findNavController(R.id.nav_host_fragment).apply {
                navigateUp()
                navigate(R.id.checkoutFragment)
            }
        }
    }

}