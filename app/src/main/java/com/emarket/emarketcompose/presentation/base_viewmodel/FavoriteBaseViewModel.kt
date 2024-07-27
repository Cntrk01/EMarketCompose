package com.emarket.emarketcompose.presentation.base_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emarket.emarketcompose.domain.repository.model.EMarketItem
import com.emarket.emarketcompose.domain.usecase.db.FavoriteUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class FavoriteBaseViewModel(
    private val favoriteUseCase: FavoriteUseCase
) : ViewModel() {

    private val _favoriteList = MutableStateFlow<List<EMarketItem>>(emptyList())
    val favoriteList: StateFlow<List<EMarketItem>> get() = _favoriteList

    internal fun refreshFavorites() = viewModelScope.launch {
        val list = withContext(Dispatchers.IO) {
            favoriteUseCase.getFavoriteList()
        }
        _favoriteList.value = list
    }

    internal fun removeFromFavorite(eMarketItem: EMarketItem) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            favoriteUseCase.deleteProducts(eMarketItem)
        }
        refreshFavorites()
    }

    internal fun addToFavorite(eMarketItem: EMarketItem) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            favoriteUseCase.addProducts(eMarketItem)
        }
        refreshFavorites()
    }

    internal suspend fun checkProduct(itemId: String): Boolean {
        val checkExist = viewModelScope.async(Dispatchers.IO) {
            favoriteUseCase.checkExistProduct(products = itemId)
        }
        return checkExist.await()
    }
}