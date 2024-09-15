package com.emarket.emarketcompose.data.repository.remote

import com.emarket.emarketcompose.data.remote.EMarketService
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import com.emarket.emarketcompose.domain.repository.model.toEMarketItem
import com.emarket.emarketcompose.domain.repository.model.toFilterItem
import com.emarket.emarketcompose.domain.repository.remote.EMarketRemoteRepository
import com.emarket.emarketcompose.utils.Constants
import com.emarket.emarketcompose.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EMarketRemoteRepositoryImpl @Inject constructor(
    private val remoteApi: EMarketService
) : EMarketRemoteRepository {

    private var filterList = listOf<FilterItem>()

    override suspend fun getData(
        totalPageItem: Int,
        listSize: (Int) -> Unit,
        filterList : (List<FilterItem>) -> Unit
    ): Flow<Response<List<EMarketItem>>> {
        return flow {
            try {

                val fetchData = remoteApi
                    .getMarketData()
                    .map { it.toEMarketItem() }

                if (totalPageItem<1){
                    val fetchFilterList = remoteApi
                        .getMarketData()
                        .map { it.toFilterItem() }

                    this@EMarketRemoteRepositoryImpl.filterList = fetchFilterList

                    filterList(fetchFilterList)
                }

                val totalDataSize = fetchData.size
                listSize(totalDataSize)

                val endIndex = minOf(totalPageItem + Constants.PAGE_SIZE, totalDataSize)

                if (totalPageItem < endIndex) {
                    val filteredData = fetchData.subList(totalPageItem, endIndex)
                    emit(Response.Success(data = filteredData))
                }
                else {
                    emit(Response.Error("No more data available"))
                }
            } catch (e: Exception) {
                emit(Response.Error("Failed to fetch data: ${e.message}"))
            }
        }
    }
}