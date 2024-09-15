package com.emarket.emarketcompose.data.repository.local

import android.util.Log
import com.emarket.emarketcompose.data.db.EMarketDb
import com.emarket.emarketcompose.data.remote.EMarketFavoriteRepository
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import javax.inject.Inject

class EMarketFavoriteRepositoryImpl @Inject constructor(private val eMarketDbService: EMarketDb)
    : EMarketFavoriteRepository {

    override fun getProducts(): List<EMarketItem> {
        return runCatching {
            eMarketDbService.favoriteDao().getProducts()
        }.onFailure { exception ->
            when(exception){
                is IllegalStateException -> Log.e("IllegalStateException",exception.message.toString())
                is NullPointerException -> Log.e("NullPointerException",exception.message.toString())
                is RuntimeException -> Log.e("RuntimeException",exception.message.toString())
            }
        }.getOrElse {
            emptyList()
        }
    }

    override suspend fun addProducts(products: EMarketItem) {
        runCatching {
            eMarketDbService.favoriteDao().addProducts(products)
        }.onFailure { exception ->
            when(exception){
                is NullPointerException -> Log.e("NullPointerException",exception.message.toString())
                is RuntimeException -> Log.e("RuntimeException",exception.message.toString())
            }
        }
    }

    override suspend fun deleteProducts(productId: EMarketItem) {
        runCatching {
            eMarketDbService.favoriteDao().deleteProducts(productId = productId)
        }.onFailure { exception ->
            when(exception){
                is NullPointerException -> Log.e("NullPointerException",exception.message.toString())
                is RuntimeException -> Log.e("RuntimeException",exception.message.toString())
            }
        }
    }

    override suspend fun checkExistProduct(productId: String): Boolean {
        return runCatching {
            eMarketDbService.favoriteDao().checkExistProduct(productId = productId)
        }.onFailure { exception ->
            when(exception){
                is NullPointerException -> Log.e("NullPointerException",exception.message.toString())
                is RuntimeException -> Log.e("RuntimeException",exception.message.toString())
            }
        }.getOrDefault(false)
    }
}