package com.emarket.emarketcompose.domain.usecase.splash_screen

import javax.inject.Inject

data class ManageAppEntryUseCase @Inject constructor(
    val readAppEntry: ReadAppEntry,
    val saveAppEntry: SaveAppEntry
)