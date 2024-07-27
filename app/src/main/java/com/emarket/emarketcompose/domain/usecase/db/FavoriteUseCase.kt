package com.emarket.emarketcompose.domain.usecase.db

import com.emarket.emarketcompose.data.repository.local.EMarketFavoriteRepositoryImpl
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import javax.inject.Inject

class FavoriteUseCase @Inject constructor(
    private val eMarketFavoriteRepositoryImpl: EMarketFavoriteRepositoryImpl
){
    fun getFavoriteList() = eMarketFavoriteRepositoryImpl.getProducts()
    suspend fun addProducts(products: EMarketItem) = eMarketFavoriteRepositoryImpl.addProducts(products)
    suspend fun deleteProducts(products: EMarketItem) = eMarketFavoriteRepositoryImpl.deleteProducts(products)
    suspend fun checkExistProduct(products: String) = eMarketFavoriteRepositoryImpl.checkExistProduct(products)
}