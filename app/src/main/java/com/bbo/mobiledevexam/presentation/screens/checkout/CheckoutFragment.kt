package com.bbo.mobiledevexam.presentation.screens.checkout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.databinding.ActivityMainBinding
import com.bbo.mobiledevexam.databinding.FragmentCheckoutBinding
import com.bbo.mobiledevexam.presentation.screens.main.MainActivity


class CheckoutFragment : Fragment(), CheckoutViewModel.Callback {

    private lateinit var binding: FragmentCheckoutBinding

    private lateinit var viewModel: CheckoutViewModel

    private lateinit var factory: CheckoutViewModelFactory

    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = activity as MainActivity

        factory = CheckoutViewModelFactory(mainActivity.repository)

        viewModel = ViewModelProvider(this, factory).get(CheckoutViewModel::class.java)
        viewModel.callback = this

        binding = DataBindingUtil.inflate<FragmentCheckoutBinding>(layoutInflater, R.layout.fragment_checkout, container, false).apply {
            lifecycleOwner = this@CheckoutFragment
            viewmodel = viewModel
        }

        return binding.root
    }

    override fun isAgreedToTermAndConditions(): Boolean = binding.switchTac.isChecked
}