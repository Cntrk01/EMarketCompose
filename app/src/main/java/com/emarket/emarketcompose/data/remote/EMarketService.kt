package com.emarket.emarketcompose.data.remote

import com.emarket.emarketcompose.data.dto.EMarketResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EMarketService {

    @GET("products")
    suspend fun getMarketData(
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ) : EMarketResponse

    @GET("products")
    suspend fun searchMarketData(
        @Query("search") query: String
    ) : EMarketResponse

}