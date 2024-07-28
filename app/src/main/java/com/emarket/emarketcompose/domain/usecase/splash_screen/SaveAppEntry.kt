package com.emarket.emarketcompose.domain.usecase.splash_screen

import com.emarket.emarketcompose.domain.repository.local.EMarketRepository
import javax.inject.Inject


class SaveAppEntry @Inject constructor(
    private val eMarketRepository: EMarketRepository
) {
    suspend fun saveData() {
        return eMarketRepository.saveAppEntry()
    }
}