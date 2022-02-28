package com.dev.test.di

import com.dev.test.Network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesUrl() = "https://v6.exchangerate-api.com/v6/a8342a975f0cbd1a1792277a/"

    @Provides
    @Singleton
    fun providesApiService(url:String) : ApiService =
        Retrofit.Builder()

            .baseUrl(url)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())

            .build()
            .create(ApiService::class.java)
    private var httpClient = OkHttpClient.Builder()
         .retryOnConnectionFailure(true)
         .addInterceptor(
            HttpLoggingInterceptor().apply {
                this.level = HttpLoggingInterceptor.Level.BODY
            })





}