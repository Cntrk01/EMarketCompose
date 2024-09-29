package com.emarket.emarketcompose.data.repository.remote

import com.emarket.emarketcompose.data.remote.EMarketService
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.toEMarketItem
import com.emarket.emarketcompose.domain.repository.remote.EMarketRemoteRepository
import com.emarket.emarketcompose.utils.Response
import com.emarket.emarketcompose.utils.handleFlowWithError
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EMarketRemoteRepositoryImpl @Inject constructor(
    private val apiService: EMarketService
) : EMarketRemoteRepository {

    override suspend fun getData(
        pageIndex: Int,
    ): Flow<Response<List<EMarketItem>>> {
        return handleFlowWithError(
            body = {
                apiService.getMarketData(10, pageIndex).map { it.toEMarketItem() }
            },
            exceptionLamb = { e -> }
        )
    }

    override suspend fun searchData(query: String): Flow<Response<List<EMarketItem>>> {
        return handleFlowWithError(
            body = {
                apiService.searchMarketData(query).map { it.toEMarketItem() }
            },
            exceptionLamb = { e -> }
        )
    }
}