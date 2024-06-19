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
    private var fetchPageItem = 0

    suspend fun getData(
        pageIndex: Int,
        listSize: (Int) -> Unit
    ): Flow<Response<List<EMarketItem>>> {
        fetchPageItem = pageIndex * Constants.PAGE_SIZE

        val responseFlow = eMarketRemoteRepositoryImpl.getData(fetchPageItem)
        { maxSize ->
            listSize(maxSize)
        }

        return responseFlow
    }
}