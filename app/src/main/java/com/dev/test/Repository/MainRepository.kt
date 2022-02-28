package com.dev.test.Repository

import com.dev.test.Model.CurrenciesHistory
import com.dev.test.Model.CurrrencyModel
import com.dev.test.Network.ApiServiceImpl
import com.dev.test.local.LocalDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val apiServiceImpl: ApiServiceImpl,private val localDao: LocalDao) {

    fun getCurrencies():Flow<CurrrencyModel> = flow {
        emit(apiServiceImpl.getcurrencies())
    }.flowOn(Dispatchers.IO)

    fun getCurrenciesByBase(baseCurrency:String):Flow<CurrrencyModel> = flow {
        emit(apiServiceImpl.getcurrenciesByBase(baseCurrency))
    }.flowOn(Dispatchers.IO)

    fun saveCurrencyAction(currenciesHistory: CurrenciesHistory) = flow <CurrenciesHistory>{
        localDao.insertCurrencies(currenciesHistory)
        emit(currenciesHistory)
    }.flowOn(Dispatchers.IO)

    fun getCurrenciesAction(): Flow<List<CurrenciesHistory>> = flow {
       emit(localDao.getAllCurrencies())
    }.flowOn(Dispatchers.IO)
}