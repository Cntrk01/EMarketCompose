package com.emarket.emarketcompose.presentation.home

import com.emarket.emarketcompose.domain.repository.model.EMarketItem

data class HomeState(
    val error : String = "",
    val loading : Boolean = true,
    val dataList : List<EMarketItem> ?= null
)
