package com.emarket.emarketcompose.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emarket.emarketcompose.domain.repository.model.EMarketItem

@Dao
interface EMarketDao {

    @Query("SELECT * FROM products")
    fun getProducts() : List<EMarketItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProducts(products: EMarketItem)

    @Delete
    suspend fun deleteProducts(productId: EMarketItem)

    @Query("SELECT EXISTS (SELECT * FROM products WHERE itemId = :productId)")
    suspend fun checkExistProduct(productId: String) : Boolean
}