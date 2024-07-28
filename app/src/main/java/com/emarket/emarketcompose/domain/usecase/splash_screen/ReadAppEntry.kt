package com.emarket.emarketcompose.domain.usecase.splash_screen

import com.emarket.emarketcompose.domain.repository.local.EMarketRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAppEntry @Inject constructor(
    private val eMarketRepository: EMarketRepository
) {
    fun readData() : Flow<Boolean>{
        return eMarketRepository.readAppEntry()
    }
}