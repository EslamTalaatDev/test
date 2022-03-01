package com.dev.test.ui.details.viewmodel

import android.content.Context
import androidx.databinding.ObservableField
import com.dev.test.model.CurrenciesHistory

class CurrenciesHistoryItemViewModel(
    context: Context,
    var currency: CurrenciesHistory,
    var listener: CurrencyClick
) {


    val from: ObservableField<String> = ObservableField(currency.from)
    val to: ObservableField<String> = ObservableField(currency.to)
    val value: ObservableField<String> = ObservableField(currency.value)


    fun onCurrencyClick(posation: Int) {
        listener.onItemClick(
            currency = CurrenciesHistory(
                from = from.get().toString(),
                to = to.get().toString(),
                value = value.get().toString()
            ),
            posation = posation
        )
    }


    interface CurrencyClick {
        fun onItemClick(currency: CurrenciesHistory, posation: Int)
    }
}
