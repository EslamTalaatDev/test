package com.dev.test.Network

import com.dev.test.Model.CurrrencyModel
import com.dev.test.Network.ApiService
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getcurrencies(): CurrrencyModel = apiService.getcurrencies()
    suspend fun getcurrenciesByBase(baseCurrency: String): CurrrencyModel = apiService.getcurrenciesByBase(baseCurrency)
}