package com.dev.test.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dev.test.R
import com.dev.test.util.ApiState
import com.dev.test.ui.home.viewmodel.MainViewModel
import com.dev.test.databinding.FragmentHomefragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class Homefragment : Fragment() {
    lateinit var mainViewModel: MainViewModel
    lateinit var binding: FragmentHomefragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_homefragment, container, false)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val view = binding.root
        binding.lifecycleOwner = this
        binding.model = mainViewModel
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    fun setup() {
        if (mainViewModel.list.isEmpty())
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