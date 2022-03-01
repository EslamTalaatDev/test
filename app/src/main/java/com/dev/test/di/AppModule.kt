package com.dev.test.di

import android.content.Context
import com.dev.test.data.network.ApiService
import com.dev.test.data.local.AppDatabase
import com.dev.test.ui.adapter.CurrenciesHistoryAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesApiService(url: String): ApiService =
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


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideDao(db: AppDatabase) = db.LocalDao()

    @Singleton
    @Provides
    fun curranciesAdapter(): CurrenciesHistoryAdapter = CurrenciesHistoryAdapter(ArrayList())

}