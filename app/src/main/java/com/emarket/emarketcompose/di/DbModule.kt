package com.emarket.emarketcompose.di

import android.content.Context
import androidx.room.Room
import com.emarket.emarketcompose.data.db.EMarketDb
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
               name = "emarket.db")
           .fallbackToDestructiveMigration()
           .allowMainThreadQueries()
           .build()
    }
}