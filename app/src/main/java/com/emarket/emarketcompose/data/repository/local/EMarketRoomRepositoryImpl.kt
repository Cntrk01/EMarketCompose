package com.emarket.emarketcompose.data.repository.local

import android.util.Log
import com.emarket.emarketcompose.data.db.EMarketDb
import com.emarket.emarketcompose.data.remote.EMarketDbService
import com.emarket.emarketcompose.domain.repository.model.EMarketItem

class EMarketRoomRepositoryImpl(private val eMarketDbService: EMarketDb) : EMarketDbService {
    override fun getProducts(): List<EMarketItem> {
        TODO("Not yet implemented")
    }

    override suspend fun addProducts(products: EMarketItem) {
        try {
            eMarketDbService.dbDao().addProducts(products = products)
        }catch (e:Exception){
            Log.e("Exception",e.message.toString())
        }catch (e:RuntimeException){
            Log.e("RuntimeException",e.message.toString())
        }
    }

    override suspend fun deleteProducts(productId: EMarketItem) {
        try {
            eMarketDbService.dbDao().deleteProducts(productId = productId)
        }catch (e:Exception){
            Log.e("Exception",e.message.toString())
        }catch (e:RuntimeException){
            Log.e("RuntimeException",e.message.toString())
        }
    }

    override suspend fun checkExistProduct(productId: String): Boolean {
        return try {
            eMarketDbService.dbDao().checkExistProduct(productId = productId)
        }catch (e:Exception){
            Log.e("Exception",e.message.toString())
            return false
        }
    }
}