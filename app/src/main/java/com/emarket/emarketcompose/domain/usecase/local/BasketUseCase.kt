package com.emarket.emarketcompose.domain.usecase.local

import androidx.lifecycle.LiveData
import com.emarket.emarketcompose.data.repository.local.EMarketBasketRepositoryImpl
import com.emarket.emarketcompose.domain.repository.model.BasketItem
import javax.inject.Inject

class BasketUseCase @Inject constructor(
    private val basketRepository: EMarketBasketRepositoryImpl
) {

    fun getAllBasket(): List<BasketItem> {
        return basketRepository.getAllBasket()
    }

    suspend fun getProductById(productId: String): BasketItem? {
        return basketRepository.getProductById(productId = productId)
    }

    suspend fun  addProductInBasket(product: BasketItem) {
        val checkProduct = getProductById(productId = product.productId)

        if (checkProduct?.productId?.isNotEmpty() == true){
            if (checkProduct.productId == product.productId) {
                basketRepository.updateProductQuantity(basketId = product.productId, count = checkProduct.productCount + 1)
            }
        }else{
            basketRepository.addProductInBasket(product = product)
        }
    }

    suspend fun deleteProductFromBasket(productId: String) {
        val checkProduct = getProductById(productId = productId)
        if (checkProduct?.productId?.isNotEmpty() == true){
            if (checkProduct.productId == productId) {
                if (checkProduct.productCount > 0){
                    basketRepository.updateProductQuantity(basketId = productId, count = checkProduct.productCount - 1)
                }

            }
        }else{
            basketRepository.deleteProductInBasket(productId = productId)
        }
    }

    suspend fun completeBasket() {
        basketRepository.completeBasket()
    }
}