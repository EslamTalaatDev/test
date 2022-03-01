package com.dev.test.data.network

import com.dev.test.model.CurrrencyModel
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getcurrencies(): CurrrencyModel = apiService.getcurrencies()
    suspend fun getcurrenciesByBase(baseCurrency: String): CurrrencyModel = apiService.getcurrenciesByBase(baseCurrency)
}