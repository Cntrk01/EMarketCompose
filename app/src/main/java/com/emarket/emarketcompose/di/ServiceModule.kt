package com.emarket.emarketcompose.di

import com.emarket.emarketcompose.data.remote.EMarketService
import com.emarket.emarketcompose.data.repository.remote.EMarketRemoteRepositoryImpl
import com.emarket.emarketcompose.domain.repository.remote.EMarketRemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    private const val baseUrl = "https://5fc9346b2af77700165ae514.mockapi.io/"

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): EMarketService {
        return Retrofit
            .Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EMarketService::class.java)
    }

    @Provides
    @Singleton
    fun provideEMarketRepositoryImpl(
        remoteApi : EMarketService
    ) : EMarketRemoteRepository{
        return EMarketRemoteRepositoryImpl(apiService = remoteApi)
    }
}