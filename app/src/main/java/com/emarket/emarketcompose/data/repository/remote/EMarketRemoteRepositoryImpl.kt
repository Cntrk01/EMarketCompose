package com.emarket.emarketcompose.data.repository.remote

import com.emarket.emarketcompose.data.remote.EMarketService
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import com.emarket.emarketcompose.domain.repository.model.toEMarketItem
import com.emarket.emarketcompose.domain.repository.remote.EMarketRemoteRepository
import com.emarket.emarketcompose.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EMarketRemoteRepositoryImpl @Inject constructor(
    private val apiService: EMarketService
) : EMarketRemoteRepository {

    override suspend fun getData(
        pageIndex: Int,
    ): Flow<Response<List<EMarketItem>>> {
        return flow {
            try {
                val fetchData = apiService.getMarketData(10, pageIndex).map { it.toEMarketItem() }
                emit(Response.Success(data = fetchData))
            } catch (e: Exception) {
                emit(Response.Error("Failed to fetch data: ${e.message}"))
            }
        }
    }

    override suspend fun searchData(query: String): Flow<Response<List<EMarketItem>>> {
        return flow {
            try {
                val fetchData = apiService.searchMarketData(query).map { it.toEMarketItem() }
                emit(Response.Success(data = fetchData))
            } catch (e: Exception) {
                emit(Response.Error("Failed to fetch data: ${e.message}"))
            }
        }
    }
}