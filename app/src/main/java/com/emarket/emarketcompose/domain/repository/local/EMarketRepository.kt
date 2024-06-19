package com.emarket.emarketcompose.domain.repository.local

import kotlinx.coroutines.flow.Flow

interface EMarketRepository {

    suspend fun saveAppEntry()

    fun readAppEntry() : Flow<Boolean>
}