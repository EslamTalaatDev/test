package com.dev.test.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.test.repository.MainRepository
import com.dev.test.util.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel
@Inject
constructor(private val mainRepository: MainRepository) : ViewModel() {
    private val postStateFlow: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)
    val _postStateFlow: StateFlow<ApiState> = postStateFlow

    fun getAllCurrancies() = viewModelScope.launch {
        mainRepository.getCurrenciesAction()
            .catch { e ->
                postStateFlow.value = ApiState.Failure(e)
            }.collect { data ->
                postStateFlow.value=ApiState.Success(data)
            }
    }





}