package com.bbo.mobiledevexam.presentation.screens.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import com.bbo.mobiledevexam.presentation.screens.main.MainActivity
import com.bbo.mobiledevexam.util.extension.makeGone
import com.bbo.mobiledevexam.util.extension.makeVisible
import kotlinx.android.synthetic.main.activity_main.*

class CartFragment : Fragment() {

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

        adapter = CartAdapter(CartAdapter.Listener {id ->
            viewModel.deleteItem(id)
        })

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

                adapter.submitList(it)
            }
        })
    }

}