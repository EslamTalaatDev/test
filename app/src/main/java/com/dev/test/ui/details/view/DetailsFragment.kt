package com.dev.test.ui.details.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dev.test.model.CurrenciesHistory
import com.dev.test.R
import com.dev.test.util.ApiState
import com.dev.test.ui.details.viewmodel.DetailsViewModel
import com.dev.test.databinding.FragmentDetailsBinding
import com.dev.test.ui.adapter.CurrenciesHistoryAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment(), CurrenciesHistoryAdapter.CurrencyAdapterListener {

    @Inject
    lateinit var adapter: CurrenciesHistoryAdapter
    lateinit var mainViewModel: DetailsViewModel
    lateinit var binding: FragmentDetailsBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        mainViewModel = ViewModelProvider(requireActivity()).get(DetailsViewModel::class.java)
        val view = binding.root
        binding.viewModel = mainViewModel
        return view
    }


    fun setup() {
        mainViewModel.getAllCurrancies()
        lifecycleScope.launchWhenStarted {
            mainViewModel._postStateFlow.collect {
                when (it) {

                    is ApiState.Success -> {
                        recyclerImp(it.data as List<CurrenciesHistory>)

                    }
                    is ApiState.Empty -> {

                    }
                    else -> {
                    }
                }
            }
        }
    }

    fun recyclerImp(currencyList: List<CurrenciesHistory>) {
        adapter.clear()
        adapter.addItems(currencyList)
        binding.rvHistory.adapter = adapter
        adapter.setListener(this)
    }

    override fun click(optionData: CurrenciesHistory, posation: Int) {

    }
}