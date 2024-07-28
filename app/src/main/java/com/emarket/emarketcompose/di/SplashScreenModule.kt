package com.emarket.emarketcompose.di

import android.app.Application
import com.emarket.emarketcompose.data.repository.local.EMarketSplashScreenRepositoryImpl
import com.emarket.emarketcompose.domain.repository.local.EMarketRepository
import com.emarket.emarketcompose.domain.usecase.splash_screen.ManageAppEntryUseCase
import com.emarket.emarketcompose.domain.usecase.splash_screen.ReadAppEntry
import com.emarket.emarketcompose.domain.usecase.splash_screen.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SplashScreenModule {

    @Singleton
    @Provides
    fun provideManageAppRepositoryImpl(
        myContext: Application
    ): EMarketRepository = EMarketSplashScreenRepositoryImpl(context = myContext)

    @Singleton
    @Provides
    fun provideManageAppUseCase(
        eMarketRepository: EMarketRepository
    ): ManageAppEntryUseCase {
        return ManageAppEntryUseCase(
            readAppEntry = ReadAppEntry(eMarketRepository = eMarketRepository),
            saveAppEntry = SaveAppEntry(eMarketRepository = eMarketRepository)
        )
    }
}