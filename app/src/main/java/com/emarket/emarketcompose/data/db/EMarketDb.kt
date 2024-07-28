package com.emarket.emarketcompose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.emarket.emarketcompose.domain.repository.model.BasketItem
import com.emarket.emarketcompose.domain.repository.model.EMarketItem

@Database(entities = [(EMarketItem::class), (BasketItem::class)],
    version = 2,
    exportSchema = false
)
@TypeConverters(FilterItemConventers::class)
abstract class EMarketDb : RoomDatabase() {
    abstract fun favoriteDao(): EMarketFavoriteDao
    abstract fun basketDao () : EMarketBasketDao
}