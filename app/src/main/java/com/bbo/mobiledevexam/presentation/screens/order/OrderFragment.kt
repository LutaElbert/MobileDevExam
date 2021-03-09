package com.bbo.mobiledevexam.presentation.screens.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.databinding.FragmentOrderBinding
import com.bbo.mobiledevexam.presentation.screens.main.MainActivity

class OrderFragment : Fragment(), OrderViewModel.Callback {

    private lateinit var binding: FragmentOrderBinding

    private lateinit var viewModel: OrderViewModel

    private lateinit var factory: OrderViewModelFactory

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = activity as MainActivity

        factory = OrderViewModelFactory(mainActivity.application ,mainActivity.repository)



        viewModel = ViewModelProvider(this, factory).get(OrderViewModel::class.java)
        viewModel.callback = this

        binding = FragmentOrderBinding.inflate(layoutInflater, container, false)
        binding.viewmodel = viewModel

        return binding.root
    }

    override fun onClickReturnToProducts() {
        activity?.let {
            it.findNavController(R.id.nav_host_fragment).apply {
                navigateUp()
                navigate(R.id.productListFragment)
            }
        }
    }

}