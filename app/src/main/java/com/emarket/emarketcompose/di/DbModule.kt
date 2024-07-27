package com.emarket.emarketcompose.di

import android.content.Context
import androidx.room.Room
import com.emarket.emarketcompose.data.db.EMarketDb
import com.emarket.emarketcompose.data.repository.local.EMarketFavoriteRepositoryImpl
import com.emarket.emarketcompose.domain.usecase.db.FavoriteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideRoomDb(
        @ApplicationContext context: Context
    ): EMarketDb {
        return Room
            .databaseBuilder(
                context = context,
                klass = EMarketDb::class.java,
                name = "emarket.db1"
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
        eMarketFavoriteRepositoryImpl: EMarketFavoriteRepositoryImpl
    ) : FavoriteUseCase{
        return FavoriteUseCase(eMarketFavoriteRepositoryImpl = eMarketFavoriteRepositoryImpl)
    }
}