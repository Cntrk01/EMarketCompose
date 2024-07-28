package com.emarket.emarketcompose.domain.repository.remote

import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem
import com.emarket.emarketcompose.utils.Response
import kotlinx.coroutines.flow.Flow

interface EMarketRemoteRepository {
    suspend fun getData(
        totalPageItem: Int, listSize: (Int) -> Unit,
        filterList: (List<FilterItem>) -> Unit
    ): Flow<Response<List<EMarketItem>>>
}