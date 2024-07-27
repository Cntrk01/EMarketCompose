package com.emarket.emarketcompose.data.repository.local

import android.util.Log
import com.emarket.emarketcompose.data.db.EMarketDb
import com.emarket.emarketcompose.data.remote.EMarketDbService
import com.emarket.emarketcompose.domain.repository.model.EMarketItem

class EMarketFavoriteRepositoryImpl(private val eMarketDbService: EMarketDb) : EMarketDbService {
    override fun getProducts(): List<EMarketItem> {
        return runCatching {
            eMarketDbService.dbDao().getProducts()
        }.onFailure { exception ->
            when(exception){
                is RuntimeException -> Log.e("RuntimeException",exception.message.toString())
                is Exception -> Log.e("Exception",exception.message.toString())
            }
        }.getOrElse {
            emptyList()
        }
    }

    override suspend fun addProducts(products: EMarketItem) {
        runCatching {
            eMarketDbService.dbDao().addProducts(products)
        }.onFailure { exception ->
            when(exception){
                is RuntimeException -> Log.e("RuntimeException",exception.message.toString())
                is Exception -> Log.e("Exception",exception.message.toString())
            }
        }
    }

    override suspend fun deleteProducts(productId: EMarketItem) {
        runCatching {
            eMarketDbService.dbDao().deleteProducts(productId = productId)
        }.onFailure { exception ->
            when(exception){
                is RuntimeException -> Log.e("RuntimeException",exception.message.toString())
                is Exception -> Log.e("Exception",exception.message.toString())
            }
        }
    }

    override suspend fun checkExistProduct(productId: String): Boolean {
        return runCatching {
            eMarketDbService.dbDao().checkExistProduct(productId = productId)
        }.onFailure { exception ->
            when(exception){
                is RuntimeException -> Log.e("RuntimeException",exception.message.toString())
                is Exception -> Log.e("Exception",exception.message.toString())
            }
        }.getOrDefault(false)
    }
}