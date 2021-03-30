package com.bbo.mobiledevexam.presentation.screens.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.bbo.mobiledevexam.R
import com.bbo.mobiledevexam.databinding.ActivityMainBinding
import com.bbo.mobiledevexam.db.ProductDatabase
import com.bbo.mobiledevexam.db.ProductRepository

class MainActivity : AppCompatActivity(), MainActivityViewModel.Callback {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel

    lateinit var repository: ProductRepository

    companion object {
        const val TAG = "MainActivity"
    }

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

    override fun onClickCart() {
        findNavController(R.id.nav_host_fragment).apply {
            navigateUp()
            navigate(R.id.cartFragment)
        }
    }

    fun onError(message: String?) {
        val msg = message ?: return
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}