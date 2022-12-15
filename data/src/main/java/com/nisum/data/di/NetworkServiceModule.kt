package com.nisum.data.di

import com.nisum.data.BuildConfig
import com.nisum.data.source.remote.ApiService
import com.nisum.data.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkServiceModule @Inject constructor() {
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (BuildConfig.DEBUG) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }
            return loggingInterceptor
        }

    init {
        okHttpBuilder.addInterceptor(logger)
        okHttpBuilder.connectTimeout(Constants.timeoutConnect.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(Constants.timeoutRead.toLong(), TimeUnit.SECONDS)
        val client = okHttpBuilder.build()
        retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(): ApiService = retrofit.create(ApiService::class.java)
}