package com.dev.test.allViewModel

import android.text.Editable
import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.test.Model.CurrenciesDetails
import com.dev.test.Repository.MainRepository
import com.dev.test.Util.ApiState
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
    fun getCurrenciesByBase(name:String) = viewModelScope.launch {
        postStateFlow.value = ApiState.Loading

        mainRepository.getCurrenciesByBase(name)
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
    fun onSelectItemFrom(posation:Int){

        if(list.isNotEmpty()){
            getCurrenciesByBase(curranciesList[posation])
        }

    }
    fun onSelectItemTo(posation:Int){
        currencyToText.set(curranciesList[posation])

    }
    fun getConversvalue(text: Editable){

            var toValue = list.filter { it.name ==currencyToText.get().toString() }.single().value!!.toDouble()
            converseValue.set((currencyFromText.get().toString().toDouble()*toValue).toString())

      }
    fun swapbutton(v:View){
        var olFromValue=selectedCurrFrom.get()
        var olToValue=selectedCurrTo.get()
        selectedCurrTo.set(olFromValue)
        selectedCurrFrom.set(olToValue)
    }

}