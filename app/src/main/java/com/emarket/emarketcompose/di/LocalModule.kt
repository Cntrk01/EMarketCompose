package com.emarket.emarketcompose.di

import android.content.Context
import androidx.room.Room
import com.emarket.emarketcompose.data.db.EMarketDb
import com.emarket.emarketcompose.data.repository.local.EMarketBasketRepositoryImpl
import com.emarket.emarketcompose.data.repository.local.EMarketFavoriteRepositoryImpl
import com.emarket.emarketcompose.domain.usecase.local.BasketUseCase
import com.emarket.emarketcompose.domain.usecase.local.FavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideRoomDb(
        @ApplicationContext context: Context
    ): EMarketDb {
        return Room
            .databaseBuilder(
                context = context,
                klass = EMarketDb::class.java,
                name = "emarket.db2"
            )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    fun provideEMarketFavoriteRepositoryImpl(
        eMarketDb: EMarketDb
    ): EMarketFavoriteRepositoryImpl {
        return EMarketFavoriteRepositoryImpl(eMarketDbService = eMarketDb)
    }

    @Singleton
    @Provides
    fun provideEMarketFavoriteUseCase(
        eMarketFavoriteRepositoryImpl: EMarketFavoriteRepositoryImpl,
        basketUseCase: BasketUseCase
    ): FavoriteUseCase {
        return FavoriteUseCase(
            eMarketFavoriteRepositoryImpl = eMarketFavoriteRepositoryImpl,
            basketUseCase = basketUseCase
        )
    }

    @Singleton
    @Provides
    fun provideEMarketBasketRepositoryImpl(
        eMarketDb: EMarketDb
    ): EMarketBasketRepositoryImpl {
        return EMarketBasketRepositoryImpl(marketDb = eMarketDb)
    }

    @Singleton
    @Provides
    fun provideEMarketBasketUseCasee(
        basketRepositoryImpl: EMarketBasketRepositoryImpl
    ): BasketUseCase {
        return BasketUseCase(basketRepository = basketRepositoryImpl)
    }
}