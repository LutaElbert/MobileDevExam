package com.bbo.mobiledevexam.presentation.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.databinding.ActivityMainBinding
import com.bbo.mobiledevexam.db.ProductDAO
import com.bbo.mobiledevexam.db.ProductDatabase
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.model.CustomFont
import com.bbo.mobiledevexam.util.extension.getCustomFont

class MainActivity : AppCompatActivity(), MainActivityViewModel.Callback {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel

    lateinit var repository: ProductRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dao = ProductDatabase.getInstance(requireNotNull(this)).productDAO
        repository = ProductRepository(dao)

        val factory = MainActivityViewModelFactory(repository)

        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)
        viewModel.callback = this

        binding.viewmodel = viewModel

    }

//    fun getDatabaseInstance() : ProductRepository {
//        val dao = ProductDatabase.getInstance(requireNotNull(this)).productDAO
//        return ProductRepository(dao)
//    }

    override fun onClickCart() {
        findNavController(R.id.nav_host_fragment).apply {
            navigateUp()
            navigate(R.id.cartFragment)
        }
    }
}