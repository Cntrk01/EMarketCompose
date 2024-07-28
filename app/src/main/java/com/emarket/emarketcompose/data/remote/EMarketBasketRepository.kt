package com.emarket.emarketcompose.data.remote

import androidx.lifecycle.LiveData
import com.emarket.emarketcompose.domain.repository.model.BasketItem

interface EMarketBasketRepository {
    fun getAllBasket() : List<BasketItem>

    suspend fun getProductById(productId: String): BasketItem?

    suspend fun addProductInBasket(product: BasketItem)

    suspend fun updateProductQuantity(basketId: String, count: Int)

    suspend fun deleteProductInBasket(productId: String)

    suspend fun completeBasket()
}