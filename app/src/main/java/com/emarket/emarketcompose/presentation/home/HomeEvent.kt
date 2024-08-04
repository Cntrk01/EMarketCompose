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

//sealed class HomeEvent {
//    abstract fun handle(viewModel: HomeViewModel)
//
//    object LoadData : HomeEvent() {
//        override fun handle(viewModel: HomeViewModel) {
//            viewModel.loadMoreDataList()
//        }
//    }
//
//    data class SearchItem(val query: String) : HomeEvent() {
//        override fun handle(viewModel: HomeViewModel) {
//            viewModel.viewModelScope.launch {
//                viewModel.searchItem(query)
//            }
//        }
//    }
//
//    data class FilterItems(val filters: List<FilterItem>) : HomeEvent() {
//        override fun handle(viewModel: HomeViewModel) {
//            viewModel._homeDataState.value = viewModel._homeDataState.value.copy(filterList = filters)
//        }
//    }
//
//    data class ClickDetail(val item: EMarketItem) : HomeEvent() {
//        override fun handle(viewModel: HomeViewModel) {
//            viewModel.viewModelScope.launch {
//                viewModel._events.emit(this@ClickDetail)
//            }
//        }
//    }
//
//    data class AddToCart(val item: EMarketItem) : HomeEvent() {
//        override fun handle(viewModel: HomeViewModel) {
//            viewModel.addToCardProduct(item)
//        }
//    }
//
//    data class ToggleFavorite(val item: EMarketItem) : HomeEvent() {
//        override fun handle(viewModel: HomeViewModel) {
//            // Favori durumu değiştirme işlemi
//        }
//    }
//
//    data class CheckProduct(val item: EMarketItem) : HomeEvent() {
//        override fun handle(viewModel: HomeViewModel) {
//            viewModel.checkProducts(item)
//        }
//    }
//
//    data class UpdateProductStatus(val item: EMarketItem) : HomeEvent() {
//        override fun handle(viewModel: HomeViewModel) {
//            viewModel.updateProductStatus(item)
//        }
//    }
//}