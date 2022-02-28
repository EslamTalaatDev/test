package com.dev.test.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.dev.test.Model.CurrenciesHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertCurrencies(currencyHistory: CurrenciesHistory)

    @Query("SELECT * FROM currencieshistory WHERE id = :currencyId")
    fun getCurrencies(currencyId: Int): Flow<CurrenciesHistory>

    @Insert(onConflict = REPLACE)
    suspend fun insertAllCurrencies(currencyHistory: List<CurrenciesHistory>)

    @Query("SELECT * FROM currencieshistory")
    fun getAllCurrencies(): List<CurrenciesHistory>

}
