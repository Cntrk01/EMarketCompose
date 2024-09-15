package com.emarket.emarketcompose.data.repository.local

import com.emarket.emarketcompose.data.db.EMarketDb
import com.emarket.emarketcompose.data.remote.EMarketBasketRepository
import com.emarket.emarketcompose.domain.repository.model.BasketItem
import javax.inject.Inject

class EMarketBasketRepositoryImpl @Inject constructor(
    private val marketDb : EMarketDb
) : EMarketBasketRepository{

    override fun getAllBasket(): List<BasketItem> {
        return marketDb.basketDao().getAllBasket()
    }

    override suspend fun getProductById(productId: String): BasketItem? {
        return marketDb.basketDao().getProductById(productId = productId)
    }

    override suspend fun addProductInBasket(product: BasketItem) {
        marketDb.basketDao().addProductInBasket(product = product)
    }

    override suspend fun updateProductQuantity(basketId: String, count: Int) {
        marketDb.basketDao().updateProductQuantity(basketId = basketId, count = count)
    }

    override suspend fun deleteProductInBasket(productId: String) {
        marketDb.basketDao().deleteProductInBasket(productId = productId)
    }

    override suspend fun completeBasket() {
        marketDb.basketDao().completeBasket()
    }
}