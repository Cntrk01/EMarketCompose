package com.emarket.emarketcompose.domain.usecase.app_entry

import javax.inject.Inject

data class ManageAppEntryUseCase @Inject constructor(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry
)