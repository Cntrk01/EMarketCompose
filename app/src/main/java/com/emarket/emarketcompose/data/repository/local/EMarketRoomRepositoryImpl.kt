package com.emarket.emarketcompose.data.repository.local

import com.emarket.emarketcompose.data.remote.EMarketDbService
import com.emarket.emarketcompose.domain.repository.model.EMarketItem

class EMarketRoomRepositoryImpl : EMarketDbService {
    override fun getProducts(): List<EMarketItem> {
        TODO("Not yet implemented")
    }

    override suspend fun addProducts(products: EMarketItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteProducts(productId: String) {
        TODO("Not yet implemented")
    }
}