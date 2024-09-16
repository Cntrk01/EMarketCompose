package com.emarket.emarketcompose.domain.usecase.remote

import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import com.emarket.emarketcompose.domain.repository.remote.EMarketRemoteRepository
import com.emarket.emarketcompose.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataListUseCase @Inject constructor(
    private val eMarketRemoteRepositoryImpl: EMarketRemoteRepository
) {
    suspend fun getData(
        pageIndex: Int,
    ): Flow<Response<List<EMarketItem>>> {
        val responseFlow = eMarketRemoteRepositoryImpl.getData(pageIndex = pageIndex)
        return responseFlow
    }

    suspend fun searchData(
        query: String,
    ): Flow<Response<List<EMarketItem>>> {
        val responseFlow = eMarketRemoteRepositoryImpl.searchData(query = query)
        return responseFlow
    }
}