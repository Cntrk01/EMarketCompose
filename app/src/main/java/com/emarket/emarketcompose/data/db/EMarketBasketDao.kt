package com.emarket.emarketcompose.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.emarket.emarketcompose.domain.repository.model.BasketItem

@Dao
interface EMarketBasketDao {

    @Query("SELECT * FROM Basket")
    fun getAllBasket() : List<BasketItem>

    @Query("SELECT * FROM Basket WHERE productId = :productId")
    suspend fun getProductById(productId: String): BasketItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addProductInBasket(product: BasketItem)

    @Query("UPDATE Basket SET productCount = :count WHERE productId = :basketId")
    suspend fun updateProductQuantity(basketId: String, count: Int)

    @Query("DELETE FROM Basket WHERE productId = :productId")
    suspend fun deleteProductInBasket(productId: String)

    @Query("DELETE FROM Basket")
    suspend fun completeBasket()
}