package com.emarket.emarketcompose.di

import android.app.Application
import com.emarket.emarketcompose.data.repository.EMarketRepositoryImpl
import com.emarket.emarketcompose.domain.repository.EMarketRepository
import com.emarket.emarketcompose.domain.usecase.app_entry.ManageAppEntryUseCase
import com.emarket.emarketcompose.domain.usecase.app_entry.ReadAppEntry
import com.emarket.emarketcompose.domain.usecase.app_entry.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReadAndWriteModule {

    @Singleton
    @Provides
    fun provideManageAppRepositoryImpl(
        myContext: Application
    ): EMarketRepository = EMarketRepositoryImpl(context = myContext)

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