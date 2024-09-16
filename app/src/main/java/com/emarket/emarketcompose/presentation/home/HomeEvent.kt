package com.emarket.emarketcompose.presentation.home

import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.repository.model.FilterItem

sealed class HomeEvent {
    data object LoadData : HomeEvent()
    data class SearchItem(val query: String) : HomeEvent()
    data class FilterItems(val filters: List<FilterItem>) : HomeEvent()
    data class ClickDetail(val item: EMarketItem) : HomeEvent()
    data class AddToCart(val item: EMarketItem) : HomeEvent()
    data class CheckProduct(val item: EMarketItem) : HomeEvent()
    data class UpdateProductStatus(val item: EMarketItem) : HomeEvent()
}