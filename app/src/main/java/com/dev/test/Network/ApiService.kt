package com.dev.test.Network

import com.dev.test.Model.CurrrencyModel
import retrofit2.http.*

interface ApiService {

    @GET("latest/EGP")
    suspend fun getcurrencies(): CurrrencyModel

    @GET("latest/{base}")
    suspend fun getcurrenciesByBase(@Path("base") baseCurrency: String): CurrrencyModel
}