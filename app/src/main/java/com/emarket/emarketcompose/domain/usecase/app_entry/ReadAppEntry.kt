package com.emarket.emarketcompose.domain.usecase.app_entry

import com.emarket.emarketcompose.domain.repository.EMarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAppEntry @Inject constructor(
    private val eMarketRepository: EMarketRepository
) {
    fun readData() : Flow<Boolean>{
        return eMarketRepository.readAppEntry()
    }
}