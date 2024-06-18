package com.emarket.emarketcompose.domain.usecase.data_list

import com.emarket.emarketcompose.data.repository.remote.EMarketRemoteRepositoryImpl
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.utils.Constants
import com.emarket.emarketcompose.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataListUseCase @Inject constructor(
    private val eMarketRemoteRepositoryImpl: EMarketRemoteRepositoryImpl
) {
    private var index = 0
    private var totalPageItem = 0

    suspend fun getData(pageIndex: Int): Flow<Response<List<EMarketItem>>> {
        totalPageItem = pageIndex * Constants.PAGE_SIZE

        val totalItem = eMarketRemoteRepositoryImpl.getData(index = index, totalPageItem = totalPageItem)

        index = totalPageItem

        return totalItem
    }
}