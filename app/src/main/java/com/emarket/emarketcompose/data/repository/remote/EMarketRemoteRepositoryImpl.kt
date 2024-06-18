package com.emarket.emarketcompose.data.repository.remote

import com.emarket.emarketcompose.data.remote.EMarketService
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.toEMarketItem
import com.emarket.emarketcompose.domain.repository.remote.EMarketRemoteRepository
import com.emarket.emarketcompose.utils.Response
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EMarketRemoteRepositoryImpl @Inject constructor(
    private val remoteApi : EMarketService
) : EMarketRemoteRepository {

    override suspend fun getData(index:Int,totalPageItem:Int): Flow<Response<List<EMarketItem>>> {
        return flow {
            try {
                emit(Response.Loading())

                val data = remoteApi.getMarketData().map { it.toEMarketItem() }.subList(index,totalPageItem)

                if (data.isNotEmpty()){
                    emit(Response.Success(data = data))
                }else{
                    emit(Response.Success(data = null))
                }

            } catch (e: Exception) {
                emit(Response.Error(message = e.message.toString()))
            }
        }
    }
}