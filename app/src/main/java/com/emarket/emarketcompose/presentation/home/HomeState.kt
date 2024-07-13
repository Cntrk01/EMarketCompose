package com.emarket.emarketcompose.presentation.home

import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem

data class HomeState(
    val homeError : String = "",
    val homeLoading : Boolean = true,
    var homeDataList : List<EMarketItem> ?= null,
    var filterList : List<FilterItem> ?= null,
    val homeDataListSize : Int = 0,
    var homeSearchList : List<EMarketItem> ?= null,
)
