package com.dev.test.ui.home.viewmodel

import android.text.Editable
import android.text.TextUtils
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.Navigation
import com.dev.test.model.CurrenciesDetails
import com.dev.test.model.CurrenciesHistory
import com.dev.test.R
import com.dev.test.repository.MainRepository
import com.dev.test.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {
    val curranciesList = ObservableArrayList<String>()
    var list = ArrayList<CurrenciesDetails>()
    var converseValue: ObservableField<String?> = ObservableField()
    var selectedCurrFrom: ObservableField<Int?> = ObservableField(0)
    var selectedCurrTo: ObservableField<Int?> = ObservableField(0)
    var selectedCurrFromName: ObservableField<String?> = ObservableField("")
    var selectedCurrToName: ObservableField<String?> = ObservableField("")
    var currencyFromText: ObservableField<String?> = ObservableField("")
    var currencyToText: ObservableField<String?> = ObservableField("")
    private val postStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val _postStateFlow: StateFlow<ApiState> = postStateFlow
    fun getCurrencies() = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading
        mainRepository.getCurrencies()
            .catch { e ->
                postStateFlow.value = ApiState.Failure(e)
            }.collect { data ->

                list.clear()


                val localParams = JSONObject(data.conversionRates.toString())
                val iterator: Iterator<String> = localParams.keys()

                while (iterator.hasNext()) {
                    val key = iterator.next()
                    val value = localParams[key]
                    curranciesList.add(key)
                    list.add(CurrenciesDetails(name = key, value = value.toString()))
                }
                postStateFlow.value = ApiState.Success(list)

            }
    }

    fun getCurrenciesByBase() = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading

        mainRepository.getCurrenciesByBase(selectedCurrFromName.get().toString())
            .catch { e ->
                postStateFlow.value = ApiState.Failure(e)
            }.collect { data ->

                list.clear()

                val localParams = JSONObject(data.conversionRates.toString())
                val iterator: Iterator<String> = localParams.keys()

                while (iterator.hasNext()) {
                    val key = iterator.next()
                    val value = localParams[key]
                    curranciesList.add(key)
                    list.add(CurrenciesDetails(name = key, value = value.toString()))
                }
                postStateFlow.value = ApiState.Success(list)
            }
    }

    fun onSelectItemFrom(posation: Int) {
        selectedCurrFromName.set(curranciesList[posation])
        if (list.isNotEmpty()) {
            getCurrenciesByBase()
        }

    }

    fun onSelectItemTo(posation: Int) {
        selectedCurrToName.set(curranciesList[posation])

    }

    fun getConversvalue(text: Editable) {
        if (!TextUtils.isEmpty(currencyFromText.get().toString())) {
            var toValue =
                list.filter { it.name == selectedCurrToName.get().toString() }
                    .single().value!!.toDouble()
            converseValue.set((currencyFromText.get().toString().toDouble() * toValue).toString())

            // insert currency into database
            inserCurrncy(
                CurrenciesHistory(
                    from = selectedCurrFromName.get().toString(),
                    to = selectedCurrToName.get().toString(),
                    value = converseValue.get().toString()
                )
            )

        }
    }

    fun swapbutton(v: View) {
        var olFromValue = selectedCurrFrom.get()
        var olToValue = selectedCurrTo.get()
        selectedCurrTo.set(olFromValue)
        selectedCurrFrom.set(olToValue)
        currencyFromText.set("")
        converseValue.set("")

    }


    fun inserCurrncy(currenciesHistory: CurrenciesHistory) = viewModelScope.launch {
        mainRepository.saveCurrencyAction(currenciesHistory)
            .catch { e ->
                postStateFlow.value = ApiState.Failure(e)
            }.collect { data ->
            }
    }

    fun detailsClick(v: View) {

        Navigation.findNavController(v).navigate(R.id.detailsFragment)
    }


}