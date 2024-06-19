package com.emarket.emarketcompose.domain.usecase.app_entry

import com.emarket.emarketcompose.domain.repository.local.EMarketRepository
import javax.inject.Inject


class SaveAppEntry @Inject constructor(
    private val eMarketRepository: EMarketRepository
) {
    suspend fun saveData() {
        return eMarketRepository.saveAppEntry()
    }
}