package com.bbo.mobiledevexam.presentation.screens.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bbo.mobiledevexam.databinding.ActivityMainBinding
import com.bbo.mobiledevexam.db.ProductDatabase
import com.bbo.mobiledevexam.db.ProductRepository
import com.bbo.mobiledevexam.model.CustomFont
import com.bbo.mobiledevexam.util.extension.getCustomFont

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainActivityViewModelFactory(getDatabaseInstance())

        viewModel = ViewModelProvider(this, factory).get(MainActivityViewModel::class.java)

        binding.textTitle.typeface = getCustomFont(CustomFont.MontserratExtraBold)

    }

    private fun getDatabaseInstance() : ProductRepository {
        val dao = ProductDatabase.getInstance(requireNotNull(this)).productDAO
        return ProductRepository(dao)
    }
}