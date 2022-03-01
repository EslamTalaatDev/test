package com.dev.test.data.network

import com.dev.test.model.CurrrencyModel
import retrofit2.http.*

interface ApiService {

    @GET("latest/EGP")
    suspend fun getcurrencies(): CurrrencyModel

    @GET("latest/{base}")
    suspend fun getcurrenciesByBase(@Path("base") baseCurrency: String): CurrrencyModel
}