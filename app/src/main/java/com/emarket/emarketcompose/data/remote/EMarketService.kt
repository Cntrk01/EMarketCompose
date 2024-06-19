package com.emarket.emarketcompose.data.remote

import com.emarket.emarketcompose.data.dto.EMarketResponse
import retrofit2.http.GET

interface EMarketService {

    @GET("products")
    suspend fun getMarketData() : EMarketResponse
}