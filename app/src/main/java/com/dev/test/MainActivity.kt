package com.dev.test

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import com.dev.test.Model.CurrenciesDetails
import com.dev.test.R
import com.dev.test.Util.ApiState
import com.dev.test.allViewModel.MainViewModel
import com.dev.test.databinding.ActivityMainBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView(this, R.layout.activity_main)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.model = mainViewModel
        binding.lifecycleOwner = this
        mainViewModel.getCurrencies()



        lifecycleScope.launchWhenStarted {
            mainViewModel._postStateFlow.collect {
                when (it) {
                    is ApiState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is ApiState.Failure -> {
                        binding.progressBar.isVisible = false
                    }
                    is ApiState.Success -> {

                        binding.progressBar.isVisible = false
                     }
                    is ApiState.Empty -> {

                    }
                }
            }
        }
    }


}