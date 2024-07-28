package com.emarket.emarketcompose.data.remote

import com.emarket.emarketcompose.domain.repository.model.EMarketItem

interface EMarketFavoriteRepository {
    fun getProducts() : List<EMarketItem>
    suspend fun addProducts(products: EMarketItem)
    suspend fun deleteProducts(productId: EMarketItem)
    suspend fun checkExistProduct(productId: String) : Boolean
}